import { BatHo, BatHoService } from '../../bat-ho';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { HopDong, HopDongService, LOAIHOPDONG, TRANGTHAIHOPDONG } from '../../hop-dong';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VayLaiService, VayLai } from '../../vay-lai';
import { LichSuDongTien, DONGTIEN } from '../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuDongTienService } from '../../lich-su-dong-tien/lich-su-dong-tien.service';
import { Principal } from '../../../shared';
import { GhiNo, GhiNoService } from '../../ghi-no';
@Component({
  selector: 'jhi-chi-tiet-tien-lai',
  templateUrl: './chi-tiet-tien-lai.component.html',
  styles: []
})
export class ChiTietTienLaiComponent implements OnInit {
  batHos: BatHo[];
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  hopDong: HopDong;
  lichSuDongTienVLs: LichSuDongTien[];
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTien: LichSuDongTien;
  currentAccount: any;
  eventSubscriber: Subscription;
  hopDongs: HopDong[];
  nhanViens: NhanVien[];
  nhanVien: NhanVien;
  ghiNoVLs: GhiNo[];
  ghiNoBHs: GhiNo[];
  ghiNo: GhiNo;
  tongTienBH: number;
  tongTienVL: number;
  tongTienBHs: number;
  tongTienVLs: number;
  tongTienNoBh: number;
  tongTienTraBh: number;
  tongTienNoVl: number;
  tongTienTraVl: number;
  vayLai: VayLai;
  vayLais: VayLai[];
  constructor(
    private nhanVienService: NhanVienService,
    private batHoService: BatHoService,
    private vayLaiService: VayLaiService,
    private hopDongService: HopDongService,
    private lichSuDongTienService: LichSuDongTienService,
    private ghiNoService: GhiNoService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {

  }

  ngOnInit() {
    this.loadNhanVien();
    this.loadBatHo();
  }
  timKiem() {
    this.tongTienBHs = 0;
    this.tongTienVL = 0;
    this.batHoService.findByTrangThai(this.tungay, this.denngay,TRANGTHAIHOPDONG.DADONG).subscribe(
      (res: HttpResponse<BatHo[]>) => {
        this.batHos = res.body;
        this.batHos.forEach(element => {
          this.tongTienBHs += element.tienduakhach;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.lichSuDongTienService.baoCao(DONGTIEN.DADONG,LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienVLs = res.body;
        this.lichSuDongTienVLs.forEach(element => {
          this.tongTienVL = this.tongTienVL + element.sotien;
          console.log(element.sotien);
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
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
  loadBatHo() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.batHoService.findByTrangThai(this.tungay, this.denngay,TRANGTHAIHOPDONG.DADONG).subscribe(
      (res: HttpResponse<BatHo[]>) => {
        this.batHos = res.body;
        this.batHos.forEach(element => {
          this.tongTienBHs += element.tienduakhach;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
  loadLichSuDongTienVL() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(DONGTIEN.DADONG,LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienVLs = res.body;
        this.lichSuDongTienVLs.forEach(element => {
          this.tongTienVL += element.sotien;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

}
