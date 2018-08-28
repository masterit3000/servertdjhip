import { Component, OnInit } from '@angular/core';
import { BatHo, BatHoService } from '../../bat-ho';
import { HopDong, HopDongService, LOAIHOPDONG } from '../../hop-dong';
import { LichSuDongTien, LichSuDongTienService, DONGTIEN } from '../../lich-su-dong-tien';
import { Subscription } from '../../../../../../../node_modules/rxjs';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { GhiNo, GhiNoService } from '../../ghi-no';
import { VayLai, VayLaiService } from '../../vay-lai';
import { JhiEventManager, JhiAlertService } from '../../../../../../../node_modules/ng-jhipster';
import { Principal } from '../../../shared';
import { HttpResponse, HttpErrorResponse } from '../../../../../../../node_modules/@angular/common/http';

@Component({
  selector: 'jhi-thu-tien-vay-lai',
  templateUrl: './thu-tien-vay-lai.component.html',
  styles: []
})
export class ThuTienVayLaiComponent implements OnInit {
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
  ghiNo: GhiNo;
  tongTienBH: number;
  tongTienVL: number;
  tongTienBHs: number;
  tongTienVLs: number;
  tongTienNoVl: number;
  tongTienTraVl: number;
  tongTienVLThemBot: number;
  vayLai: VayLai;
  vayLais: VayLai[];
  vayLaiThemBot: VayLai[];
  selectedNhanVien: NhanVien;
  tongTienTraGoc: number;
  default:NhanVien;


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
    this.tongTienVLs = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
    this.tongTienVLThemBot = 0;
    this.selectedNhanVien = this.default;

  }

  ngOnInit() {
    this.loadNhanVien();
    this.loadLichSuDongTienVL();
    this.loadLichGhiNoTienVL();
    this.loadVayLai();
    this.loadLichSuDongTienTraGoc();
    this.loadVayThemTraBot();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHopDongs();
  }
  timKiem() {
    this.tongTienVL = 0;
    this.tongTienVLs = 0;
    this.tongTienNoVl = 0;
    this.tongTienTraVl = 0;
    this.tongTienTraGoc = 0;
    this.tongTienVLThemBot = 0;
    if (this.selectedNhanVien == this.default) {
      console.log(this.denngay);
      this.lichSuDongTienService.baoCao(DONGTIEN.DADONG, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienVLs = res.body;
          this.lichSuDongTienVLs.forEach(element => {
            this.tongTienVL = this.tongTienVL + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.ghiNoService.baoCao(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
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
      this.vayLaiService.baoCao(this.tungay, this.denngay,0).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLais = res.body;
          this.vayLais.forEach(element => {
            this.tongTienVLs += element.tienvay;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.vayLaiService.baoCao(this.tungay, this.denngay, 1).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLaiThemBot = res.body;
          this.vayLaiThemBot.forEach(element => {
            this.tongTienVLThemBot += element.tienvay;
          });
  
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.lichSuDongTienService.baoCao(DONGTIEN.TRAGOC, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienTraGoc = res.body;
          this.lichSuDongTienTraGoc.forEach(element => {
            this.tongTienTraGoc += element.sotien;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );


    } else {
      this.lichSuDongTienService.baoCaoNV(DONGTIEN.DADONG, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienVLs = res.body;
          this.lichSuDongTienVLs.forEach(element => {
            this.tongTienVL = this.tongTienVL + element.sotien;
            console.log(element.sotien);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.vayLaiService.baoCaoNV(this.tungay, this.denngay, this.selectedNhanVien.id,0).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLais = res.body;
          this.vayLais.forEach(element => {
            this.tongTienVLs += element.tienvay;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.vayLaiService.baoCaoNV(this.tungay, this.denngay,this.selectedNhanVien.id, 1).subscribe(
        (res: HttpResponse<VayLai[]>) => {
          this.vayLaiThemBot = res.body;
          this.vayLaiThemBot.forEach(element => {
            this.tongTienVLThemBot += element.tienvay;
          });
  
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.lichSuDongTienService.baoCaoNV(DONGTIEN.TRAGOC, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
        (res: HttpResponse<LichSuDongTien[]>) => {
          this.lichSuDongTienTraGoc = res.body;
          this.lichSuDongTienTraGoc.forEach(element => {
            this.tongTienTraGoc += element.sotien;
          });

        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.ghiNoService.baoCaoNV(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay, this.selectedNhanVien.id).subscribe(
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
    }

  }
  registerChangeInHopDongs() {
    this.eventSubscriber = this.eventManager.subscribe(
      'hopDongListModification',
      response => {
        this.loadLichSuDongTienVL();
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
  loadLichSuDongTienVL() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.lichSuDongTienService.baoCao(DONGTIEN.DADONG, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
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

  loadLichGhiNoTienVL() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.ghiNoService.baoCao(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
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

  }


  loadVayLai() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.vayLaiService.baoCao(this.tungay, this.denngay,0).subscribe(
      (res: HttpResponse<VayLai[]>) => {
        this.vayLais = res.body;
        this.vayLais.forEach(element => {
          this.tongTienVLs += element.tienvay;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }


  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
  loadLichSuDongTienTraGoc() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(DONGTIEN.TRAGOC, LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienTraGoc = res.body;
        this.lichSuDongTienTraGoc.forEach(element => {
          this.tongTienTraGoc += element.sotien;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadVayThemTraBot() {
    this.tungay = new Date();
    this.denngay = new Date();
    this.vayLaiService.baoCao(this.tungay, this.denngay, 1).subscribe(
      (res: HttpResponse<VayLai[]>) => {
        this.vayLaiThemBot = res.body;
        this.vayLaiThemBot.forEach(element => {
          this.tongTienVLThemBot += element.tienvay;
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }


}
