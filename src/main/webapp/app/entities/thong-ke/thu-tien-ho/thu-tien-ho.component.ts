
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

@Component({
  selector: 'jhi-thu-tien-ho',
  templateUrl: './thu-tien-ho.component.html',
  styles: []
})
export class  ThuTienHoComponent implements OnInit {
  batHos: BatHo[];
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  hopDong: HopDong;
  lichSuDongTienVLs: LichSuDongTien[];
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTienTraGoc: LichSuDongTien[];

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
  selectedNhanVien: NhanVien;
  tongTienTraGoc: number;



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
    this.lichSuDongTienVLs = new Array<LichSuDongTien>();
    this.lichSuDongTienBHs = new Array<LichSuDongTien>();
    this.tongTienVL = 0;
    this.tongTienBH = 0;
    this.tongTienBHs = 0;
    this.tongTienVLs = 0;
    this.tongTienNoBh = 0;
    this.tongTienTraBh = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
    this.selectedNhanVien = new NhanVien;

  }

  ngOnInit() {
    this.loadNhanVien();
    this.loadLichSuDongTienBH();
    this.loadLichGhiNoTienBH();
    this.loadBatHo();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHopDongs();
  }
  timKiem() {
    this.tongTienVL = 0;
    this.tongTienBH = 0;
    this.tongTienBHs = 0;
    this.tongTienVLs = 0;
    this.tongTienNoBh = 0;
    this.tongTienTraBh = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
    if (this.selectedNhanVien == 1) {
      console.log(this.denngay);
      this.lichSuDongTienService.baoCao(DONGTIEN.DADONG, LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienBHs = res.body;
          this.lichSuDongTienBHs.forEach(element => {
            this.tongTienBH = this.tongTienBH + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.ghiNoService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
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

      this.batHoService.baoCao(this.tungay, this.denngay).subscribe(
        (res: HttpResponse<BatHo[]>) => {
          this.batHos = res.body;
          this.batHos.forEach(element => {
            this.tongTienBHs += element.tienduakhach;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );


    } else {
      this.lichSuDongTienService.baoCaoNV(DONGTIEN.DADONG, LOAIHOPDONG.BATHO, this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienBHs = res.body;
          this.lichSuDongTienBHs.forEach(element => {
            this.tongTienBH = this.tongTienBH + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.batHoService.baoCaoNV(this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
        (res: HttpResponse<BatHo[]>) => {
          this.batHos = res.body;
          this.batHos.forEach(element => {
            this.tongTienBHs += element.tienduakhach;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );


      this.ghiNoService.baoCaoNV(LOAIHOPDONG.BATHO, this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
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
    }

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
    this.lichSuDongTienService.baoCao(DONGTIEN.DADONG, LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
        this.lichSuDongTienBHs.forEach(element => {
          this.tongTienBH += element.sotien;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadLichGhiNoTienBH() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.ghiNoService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
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

  }


  loadBatHo() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.batHoService.baoCao(this.tungay, this.denngay).subscribe(
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


}
