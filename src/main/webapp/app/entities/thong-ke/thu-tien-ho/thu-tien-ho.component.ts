import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '../../../../../../../node_modules/@angular/common/http';
import { LichSuDongTien, LichSuDongTienService } from '../../lich-su-dong-tien';
import { LOAIHOPDONG, HopDongService, HopDong } from '../../hop-dong';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { Principal } from '../../../shared';
import { BatHoService, BatHo } from '../../bat-ho';
import { JhiAlertService, JhiEventManager } from '../../../../../../../node_modules/ng-jhipster';
import { Subscription } from '../../../../../../../node_modules/rxjs';

@Component({
  selector: 'jhi-thu-tien-ho',
  templateUrl: './thu-tien-ho.component.html',
  styles: []
})
export class ThuTienHoComponent implements OnInit {
  batHos: BatHo[];
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  hopDong: HopDong;
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTien: LichSuDongTien;
  currentAccount: any;
  eventSubscriber: Subscription;
  hopDongs: HopDong[];
  nhanViens: NhanVien[];
  nhanVien: NhanVien;
  tongTienBH: number;
  constructor(
    private nhanVienService: NhanVienService,
    private batHoService: BatHoService,
    private hopDongService: HopDongService,
    private lichSuDongTienService: LichSuDongTienService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) { 
    this.lichSuDongTienBHs = new Array<LichSuDongTien>();
    this.tongTienBH = 0;
    this.nhanVien = new NhanVien;
  }

  ngOnInit() {
    this.loadLichSuDongTienBH();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHopDongs();
  }
  timKiem(){
    this.tongTienBH = 0;
    console.log(this.denngay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
        this.lichSuDongTienBHs.forEach(element => {
          this.tongTienBH = this.tongTienBH + element.sotien;
          console.log(element.sotien);
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  registerChangeInHopDongs() {
    this.eventSubscriber = this.eventManager.subscribe(
      'hopDongListModification',
      response => {
        this.loadLichSuDongTienBH();
      }
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
  loadLichSuDongTienBH() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
        this.lichSuDongTienBHs.forEach(element => {
          this.tongTienBH += element.sotien;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

}
