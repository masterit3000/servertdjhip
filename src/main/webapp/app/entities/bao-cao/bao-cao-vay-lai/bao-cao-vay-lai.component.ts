import { Component, OnInit } from '@angular/core';
import { NhanVienService, NhanVien } from '../../nhan-vien';
import { HopDong, HopDongService, LOAIHOPDONG } from '../../hop-dong';
import { BatHo } from '../../bat-ho';
import { LichSuDongTien, LichSuDongTienService } from '../../lich-su-dong-tien';
import { VayLai } from '../../vay-lai';
import { Subscription } from '../../../../../../../node_modules/rxjs';
import { GhiNo, GhiNoService } from '../../ghi-no';
import { JhiAlertService, JhiEventManager } from '../../../../../../../node_modules/ng-jhipster';
import { Principal } from '../../../shared';
import { HttpResponse, HttpErrorResponse } from '../../../../../../../node_modules/@angular/common/http';

@Component({
  selector: 'jhi-bao-cao-vay-lai',
  templateUrl: './bao-cao-vay-lai.component.html',
  styles: []
})
export class BaoCaoVayLaiComponent implements OnInit {
  vayLais: VayLai[];
  selected: VayLai;
  tungay: Date;
  denngay: Date;
  nhanviens: NhanVien[];
  hopDong: HopDong;
  lichSuDongTiens: LichSuDongTien[];
  lichSuDongTien: LichSuDongTien;
  currentAccount: any;
  eventSubscriber: Subscription;
  hopDongs: HopDong[];
  nhanViens: NhanVien[];
  nhanVien: NhanVien;
  ghiNos: GhiNo[];
  ghiNo: GhiNo;
  constructor(
    private nhanVienService: NhanVienService,
    private hopDongService: HopDongService,
    private lichSuDongTienService: LichSuDongTienService,
    private ghiNoService: GhiNoService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {
    this.lichSuDongTiens = new Array<LichSuDongTien>();
  }

  ngOnInit() {
    this.loadLichSuDongTien();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHopDongs();
  }
  timKiem() {
    console.log(this.denngay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTiens = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  registerChangeInHopDongs() {
    this.eventSubscriber = this.eventManager.subscribe(
      'hopDongListModification',
      response => this.loadLichSuDongTien()
    );
  }
  loadNhanVien() {
    this.nhanVienService.query().subscribe(
      (res: HttpResponse<NhanVien[]>) => {
        this.nhanViens = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuDongTien() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTiens = res.body;
        this.lichSuDongTiens.forEach(element => {
          console.log(element.sotien);
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadGhiNo() {
    this.ghiNoService.query().subscribe(
      (res: HttpResponse<GhiNo[]>) => {
        this.ghiNos = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

}
