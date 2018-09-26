
import { BatHo, BatHoService } from '../../bat-ho';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { HopDong, HopDongService, LOAIHOPDONG } from '../../hop-dong';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VayLaiService, VayLai } from '../../vay-lai';
import { LichSuDongTien, DONGTIEN } from '../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuDongTienService } from '../../lich-su-dong-tien/lich-su-dong-tien.service';
import { Principal } from '../../../shared';
import { GhiNo, GhiNoService } from '../../ghi-no';
import { Router } from '@angular/router';
import { CuaHang, CuaHangService } from '../../cua-hang';

@Component({
  selector: 'jhi-thong-ke-ke-toan',
  templateUrl: './thong-ke-ke-toan.component.html',
  styles: []
})
export class ThongKeKeToanComponent implements OnInit {
  batHos: BatHo[];
  batHo: BatHo;
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  hopDong: HopDong;
  lichSuDongTienVLs: LichSuDongTien[];
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTienTraGoc: LichSuDongTien[];
  default:NhanVien;

  lichSuDongTien: LichSuDongTien;
  currentAccount: any;
  eventSubscriber: Subscription;
  hopDongs: HopDong[];
  cuaHangs: CuaHang[];
  cuaHang: CuaHang;
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
  tongTienVLThemBot:number;
  vayLai: VayLai;
  vayLais: VayLai[];
  selectedCuaHang: CuaHang;
  selectedCuaHangVL: CuaHang;
  tongTienTraGoc: number;
  vayLaiThemBot: VayLai[];
  constructor(
    private cuaHangService: CuaHangService,
    private batHoService: BatHoService,
    private vayLaiService: VayLaiService,
    private hopDongService: HopDongService,
    private lichSuDongTienService: LichSuDongTienService,
    private router: Router,
    private ghiNoService: GhiNoService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal)
     {

  }

  ngOnInit() {
    this.loadCuaHang();
  }

  timKiemBH() {
    this.tongTienVL = 0;
    this.tongTienBH = 0;
    this.tongTienBHs = 0;
    this.tongTienVLs = 0;
    this.tongTienNoBh = 0;
    this.tongTienTraBh = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
      console.log(this.denngay);
      this.lichSuDongTienService.baoCaoKeToan(DONGTIEN.DADONG, LOAIHOPDONG.BATHO, this.tungay, this.denngay,this.selectedCuaHang.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienBHs = res.body;
          this.lichSuDongTienBHs.forEach(element => {
            this.tongTienBH = this.tongTienBH + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.ghiNoService.baoCaoKeToan(LOAIHOPDONG.BATHO, this.tungay, this.denngay,this.selectedCuaHang.id).subscribe(
        (res: HttpResponse<GhiNo[]>) => {
          this.ghiNoBHs = res.body;
          this.ghiNoBHs.forEach(element => {
            if (element.trangthai.toString() == 'NO') {
              this.tongTienNoBh += element.sotien;
            } else {
              this.tongTienTraBh += element.sotien;

            }
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.batHoService.baoCaoKeToan(this.tungay, this.denngay,this.selectedCuaHang.id).subscribe(
        (res: HttpResponse<BatHo[]>) => {
          this.batHos = res.body;
          this.batHos.forEach(element => {
            this.tongTienBHs += element.tienduakhach;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    

  }

  timKiemVL() {
    this.tongTienVL = 0;
    this.tongTienVLs = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
    this.tongTienVLThemBot = 0;

      this.lichSuDongTienService.baoCaoKeToan(DONGTIEN.DADONG, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay,this.selectedCuaHangVL.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienVLs = res.body;
          this.lichSuDongTienVLs.forEach(element => {
            this.tongTienVL = this.tongTienVL + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.ghiNoService.baoCaoKeToan(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay,this.selectedCuaHangVL.id).subscribe(
        (res: HttpResponse<GhiNo[]>) => {
          this.ghiNoVLs = res.body;
          this.ghiNoVLs.forEach(element => {
            if (element.trangthai.toString() == 'NO') {
              this.tongTienNoVl += element.sotien;
            } else {
              this.tongTienTraVl += element.sotien;

            }
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.vayLaiService.baoCaoKeToan(this.tungay, this.denngay,0,this.selectedCuaHangVL.id).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLais = res.body;
          this.vayLais.forEach(element => {
            this.tongTienVLs += element.tienvay;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.vayLaiService.baoCaoKeToan(this.tungay, this.denngay, 1,this.selectedCuaHangVL.id).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLaiThemBot = res.body;
          this.vayLaiThemBot.forEach(element => {
            this.tongTienVLThemBot += element.tienvay;
          });
  
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.lichSuDongTienService.baoCaoKeToan(DONGTIEN.TRAGOC, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay,this.selectedCuaHangVL.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienTraGoc = res.body;
          this.lichSuDongTienTraGoc.forEach(element => {
            this.tongTienTraGoc += element.sotien;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );



  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
  loadCuaHang() {
    this.cuaHangService.query().subscribe(
        (res: HttpResponse<CuaHang[]>) => {
            this.cuaHangs = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
    );
}

}
