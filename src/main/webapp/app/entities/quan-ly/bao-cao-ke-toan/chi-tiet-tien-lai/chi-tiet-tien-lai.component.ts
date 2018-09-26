import { Component, OnInit } from '@angular/core';
import { BatHo, BatHoService } from '../../../bat-ho';
import { NhanVien, NhanVienService } from '../../../nhan-vien';
import {
    HopDong,
    HopDongService,
    LOAIHOPDONG,
    TRANGTHAIHOPDONG
} from '../../../hop-dong';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VayLaiService, VayLai } from '../../../vay-lai';
import {
    LichSuDongTien,
    DONGTIEN
} from '../../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuDongTienService } from '../../../lich-su-dong-tien/lich-su-dong-tien.service';
import { Principal } from '../../../../shared';
import { GhiNo, GhiNoService } from '../../../ghi-no';
import { Router } from '@angular/router';
import { CuaHang, CuaHangService } from '../../../cua-hang';
@Component({
    selector: 'jhi-chi-tiet-tien-lai',
    templateUrl: './chi-tiet-tien-lai.component.html',
    styles: []
})
export class ChiTietTienLaiComponent implements OnInit {
    batHos: BatHo[];
    batHo: BatHo;
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
    tongTienTienBatHo: number;
    tongTienLaiBatHo: number;
    vayLai: VayLai;
    vayLais: VayLai[];
    selectedCuaHang: CuaHang;
    default: NhanVien;
    constructor(
        private cuaHangService: CuaHangService,
        private batHoService: BatHoService,
        private vayLaiService: VayLaiService,
        private hopDongService: HopDongService,
        private lichSuDongTienService: LichSuDongTienService,
        private ghiNoService: GhiNoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private router: Router,
        private principal: Principal
    ) {
        this.tongTienBHs = 0;
        this.tongTienVL = 0;
        this.tongTienTienBatHo = 0;
        this.tongTienLaiBatHo = 0;
    }

    ngOnInit() {
        this.loadCuaHang();
        // this.loadBatHo();
        // this.loadLichSuDongTienVL();
    }

    previousState() {
        window.history.back();
    }

    timKiem() {
        this.tongTienBHs = 0;
        this.tongTienVL = 0;
        this.tongTienTienBatHo = 0;
        this.tongTienLaiBatHo = 0;
            this.batHoService
                .findByTrangThaiKeToan(
                    this.tungay,
                    this.denngay,
                    TRANGTHAIHOPDONG.DADONG,
                    this.selectedCuaHang.id

                )
                .subscribe(
                    (res: HttpResponse<BatHo[]>) => {
                        this.batHos = res.body;
                        this.batHos.forEach(element => {
                            this.tongTienBHs += element.tienduakhach;
                            this.tongTienTienBatHo += element.tongtien;
                            this.tongTienLaiBatHo =
                                this.tongTienTienBatHo - this.tongTienBHs;
                        });
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            this.lichSuDongTienService
                .baoCaoKeToan(
                    DONGTIEN.DADONG,
                    LOAIHOPDONG.VAYLAI,
                    this.tungay,
                    this.denngay,
                    this.selectedCuaHang.id
                )
                .subscribe(
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
    loadCuaHang() {
        this.cuaHangService.query().subscribe(
            (res: HttpResponse<CuaHang[]>) => {
                this.cuaHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadBatHo() {
        this.tungay = new Date();
        this.denngay = new Date();
        this.batHoService
            .findByTrangThaiKeToan(this.tungay, this.denngay, TRANGTHAIHOPDONG.DADONG,this.selectedCuaHang.id)
            .subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                    this.batHos.forEach(element => {
                        this.tongTienBHs += element.tienduakhach;
                        this.tongTienTienBatHo += element.tongtien;
                        this.tongTienLaiBatHo =
                            this.tongTienTienBatHo - this.tongTienBHs;
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
        this.lichSuDongTienService
            .baoCaoKeToan(
                DONGTIEN.DADONG,
                LOAIHOPDONG.VAYLAI,
                this.tungay,
                this.denngay,
                this.selectedCuaHang.id
            )
            .subscribe(
                (res: HttpResponse<LichSuDongTien[]>) => {
                    this.lichSuDongTienVLs = res.body;
                    this.lichSuDongTienVLs.forEach(element => {
                        this.tongTienVL += element.sotien;
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    findBatHoByHopDong(id: number) {
        this.batHoService.findByHopDong(id).subscribe(
            (res: HttpResponse<BatHo>) => {
                this.batHo = res.body;
                this.router.navigate(['/bat-ho', this.batHo.id]);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    findVayLaiByHopDong(id: number) {
        this.vayLaiService.findByHopDong(id).subscribe(
            (res: HttpResponse<VayLai>) => {
                this.vayLai = res.body;
                this.router.navigate(['/vay-lai', this.vayLai.id]);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}
