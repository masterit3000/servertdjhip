import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { CuaHang } from '../../../cua-hang/cua-hang.model';
import { CuaHangService } from '../../../cua-hang/cua-hang.service';
import { BatHo } from '../../../bat-ho/bat-ho.model';
import { BatHoService } from '../../../bat-ho/bat-ho.service';
import { Principal } from '../../../../shared';
import { VayLai } from '../../../vay-lai/vay-lai.model';
import { VayLaiService } from '../../../vay-lai/vay-lai.service';
import { KhachHang } from '../../../khach-hang/khach-hang.model';
import { KhachHangService } from '../../../khach-hang/khach-hang.service';
import { NhanVien } from '../../../nhan-vien/nhan-vien.model';
import { NhanVienService } from '../../../nhan-vien/nhan-vien.service';
import { ThuChi, THUCHI } from '../../../thu-chi/thu-chi.model';
import { ThuChiService } from '../../../thu-chi/thu-chi.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-cua-hang-detail-admin',
    templateUrl: './cua-hang-detail.component.html'
})
export class CuaHangDetailAdminComponent implements OnInit, OnDestroy {
    cuaHang: CuaHang;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    batHos: BatHo[];
    vayLais: VayLai[];
    nhanViens: NhanVien[];
    khachHangs: KhachHang[];
    filteredKhachHangs: KhachHang[];
    filteredNhanViens: NhanVien[];
    nhanVien: any;
    khachHang: any;
    currentAccount: any;
    text: any;
    selected: BatHo;
    none: any;
    keyTimBatHo: string;
    keyTimVayLai: string;
    thuChis: ThuChi[];
    tungay: Date;
    denngay: Date;
    thuchi: ThuChi;
    isSaving: boolean;
    tongTienThu: number;
    tongTienChi: number;
    tongTienGop: number;
    tongTienRut: number;
    constructor(
        private batHoService: BatHoService,
        private vayLaiService: VayLaiService,
        private khachHangService: KhachHangService,
        private nhanVienService: NhanVienService,
        private eventManager: JhiEventManager,
        private cuaHangService: CuaHangService,
        private thuChiService: ThuChiService,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
    ) {
        this.thuchi = new ThuChi();
        this.tongTienThu = 0;
        this.tongTienChi = 0;
        this.tongTienGop = 0;
        this.tongTienRut = 0;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    
    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });

        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCuaHangs();
    }

    load(id) {
        this.tungay = new Date();
        this.denngay = new Date();
        this.cuaHangService
            .find(id)
            .subscribe((cuaHangResponse: HttpResponse<CuaHang>) => {
                this.cuaHang = cuaHangResponse.body;
                this.batHoService.findByCuaHangId(this.cuaHang.id).subscribe(
                    (res: HttpResponse<BatHo[]>) => {
                        this.batHos = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
                this.vayLaiService.getAllByCuaHang(this.cuaHang.id).subscribe(
                    (res: HttpResponse<VayLai[]>) => {
                        this.vayLais = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );

                this.khachHangService.findByCuaHang(this.cuaHang.id).subscribe(
                    (res: HttpResponse<KhachHang[]>) => {
                        this.khachHangs = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
                this.nhanVienService
                    .findNhanVienByCuaHang(this.cuaHang.id)
                    .subscribe(
                        (res: HttpResponse<NhanVien[]>) => {
                            this.nhanViens = res.body;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                    this.thuChiService.findByTimeKeToan(this.tungay, this.denngay, THUCHI.THU,this.cuaHang.id)
                    .subscribe(
                        (res: HttpResponse<ThuChi[]>)=>{
                            this.thuChis = res.body;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                
            });
        // this.batHoService                    <----------------THỦ PHẠM BUG
        //     .findByCuaHangId(this.cuaHang.id)
        //     .subscribe((batHoResponse: HttpResponse<BatHo[]>) => {
        //         this.batHos = batHoResponse.body;
        //     });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackIdBH(index: number, item: BatHo) {
        return item.id;
    }
    trackIdVL(index: number, item: VayLai) {
        return item.id;
    }
    trackIdKH(index: number, item: KhachHang) {
        return item.id;
    }
    trackIdNV(index: number, item: NhanVien) {
        return item.id;
    }

    registerChangeInCuaHangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cuaHangListModification',
            response => this.load(this.cuaHang.id)
        );
    }

    filterKhachHangs(event: any) {
        const query = event.query;
        console.log(query);
        this.khachHangService.query(query).subscribe((khachHangs: any) => {
            this.filteredKhachHangs = khachHangs;
        });
    }
    filterNhanViens(event: any) {
        const query = event.query;
        console.log(query);
        this.nhanVienService.getNhanVien(query).subscribe((nhanViens: any) => {
            this.filteredNhanViens = nhanViens;
        });
    }
    saveThu() {
        this.thuchi.thuchi = THUCHI.THU;
        this.isSaving = true;
        console.log(this.thuchi.nhanVienId);

        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi),this.thuchi.thuchi
            );
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.createByKeToan(this.thuchi,this.cuaHang.id),this.thuchi.thuchi
            );
        }
    }
    timKiemThu() {
        this.tongTienThu = 0;
        console.log(this.denngay);
        this.thuChiService
            .findByTimeKeToan(this.tungay, this.denngay, THUCHI.THU,this.cuaHang.id)
            .subscribe(
                (res: HttpResponse<ThuChi[]>) => {
                    this.thuChis = res.body;
                    this.thuChis.forEach(element => {
                        this.tongTienThu = this.tongTienThu + element.sotien;
                        console.log(element.sotien);
                        console.log(this.tongTienThu);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    saveChi() {
        this.thuchi.thuchi = THUCHI.CHI;
        this.isSaving = true;
        console.log(this.thuchi.nhanVienId);

        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi),this.thuchi.thuchi
            );
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.createByKeToan(this.thuchi,this.cuaHang.id),this.thuchi.thuchi
            );
        }
    }
    timKiemChi() {
        this.tongTienChi = 0;
        console.log(this.denngay);
        this.thuChiService
            .findByTimeKeToan(this.tungay, this.denngay, THUCHI.CHI,this.cuaHang.id)
            .subscribe(
                (res: HttpResponse<ThuChi[]>) => {
                    this.thuChis = res.body;
                    this.thuChis.forEach(element => {
                        this.tongTienChi = this.tongTienChi + element.sotien;
                        console.log(element.sotien);
                        console.log(this.tongTienChi);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    saveGop() {
        this.thuchi.thuchi = THUCHI.GOPVON;
        this.isSaving = true;
        console.log(this.thuchi.nhanVienId);

        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi),this.thuchi.thuchi
            );
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.createByKeToan(this.thuchi,this.cuaHang.id),this.thuchi.thuchi
            );
        }
    }
    timKiemGop() {
        this.tongTienGop = 0;
        console.log(this.denngay);
        this.thuChiService
            .findByTimeKeToan(this.tungay, this.denngay, THUCHI.GOPVON,this.cuaHang.id)
            .subscribe(
                (res: HttpResponse<ThuChi[]>) => {
                    this.thuChis = res.body;
                    this.thuChis.forEach(element => {
                        this.tongTienGop = this.tongTienGop + element.sotien;
                        console.log(element.sotien);
                        console.log(this.tongTienGop);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    saveRut() {
        this.thuchi.thuchi = THUCHI.RUTVON;
        this.isSaving = true;
        console.log(this.thuchi.nhanVienId);

        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi),this.thuchi.thuchi
            );
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.createByKeToan(this.thuchi,this.cuaHang.id),this.thuchi.thuchi
            );
        }
    }
    timKiemRut() {
        this.tongTienRut = 0;
        console.log(this.denngay);
        this.thuChiService
            .findByTimeKeToan(this.tungay, this.denngay, THUCHI.RUTVON,this.cuaHang.id)
            .subscribe(
                (res: HttpResponse<ThuChi[]>) => {
                    this.thuChis = res.body;
                    this.thuChis.forEach(element => {
                        this.tongTienRut = this.tongTienRut + element.sotien;
                        console.log(element.sotien);
                        console.log(this.tongTienRut);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<ThuChi>>, kieu: THUCHI) {
        result.subscribe(
            (res: HttpResponse<ThuChi>) => this.onSaveSuccess(res.body,kieu),
            (res: HttpErrorResponse) => this.onSaveError(kieu)
        );
    }
    private onSaveSuccess(result: ThuChi,kieu: THUCHI ) {
        // this.eventManager.broadcast({ name: 'thuChiListModification', content: 'OK'});
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        if(kieu==THUCHI.THU){
            this.jhiAlertService.success('servertdjhipApp.thuChi.thuSuccess', null, null);
        }
        if(kieu==THUCHI.CHI){
            this.jhiAlertService.success('servertdjhipApp.thuChi.chiSuccess', null, null);
        }
        if(kieu==THUCHI.GOPVON){
            this.jhiAlertService.success('servertdjhipApp.thuChi.gopSuccess', null, null);
        }
        if(kieu==THUCHI.RUTVON){
            this.jhiAlertService.success('servertdjhipApp.thuChi.rutSuccess', null, null);
        }
    }
    private onSaveError(kieu: THUCHI) {
        this.isSaving = false;
        if(kieu==THUCHI.CHI){
            this.jhiAlertService.error('servertdjhipApp.thuChi.chiFail', null, null);
        }
        if(kieu==THUCHI.RUTVON){
            this.jhiAlertService.error('servertdjhipApp.thuChi.rutFail', null, null);
        }
    }
}
