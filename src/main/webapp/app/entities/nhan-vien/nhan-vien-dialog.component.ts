import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NhanVien } from './nhan-vien.model';
import { NhanVienPopupService } from './nhan-vien-popup.service';
import { NhanVienService } from './nhan-vien.service';
import { Xa, XaService } from '../xa';
import { CuaHang, CuaHangService } from '../cua-hang';
import { User, UserService } from '../../shared';
import { Tinh, TinhService } from '../tinh';
import { Huyen, HuyenService } from '../huyen';

@Component({
    selector: 'jhi-nhan-vien-dialog',
    templateUrl: './nhan-vien-dialog.component.html'
})
export class NhanVienDialogComponent implements OnInit {

    nhanVien: NhanVien;
    isSaving: boolean;
    cuahangs: CuaHang[];
    users: User[];
    filteredXas: Xa[];
    filteredTinhs: Tinh[];
    filteredHuyens: Huyen[];
    xas: Xa[];
    tinhs: Tinh[];
    huyens: Huyen[];
    xa: Xa;
    tinh: Tinh;
    huyen: Huyen;
    

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private nhanVienService: NhanVienService,
        private xaService: XaService,
        private tinhService: TinhService,
        private huyenService: HuyenService,
        private cuaHangService: CuaHangService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.xaService.query()
            .subscribe((res: HttpResponse<Xa[]>) => { this.xas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cuaHangService.query()
            .subscribe((res: HttpResponse<CuaHang[]>) => { this.cuahangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.queryUserNew()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.nhanVien.xaId = this.xa.id;
        if (this.nhanVien.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nhanVienService.update(this.nhanVien));
        } else {
            this.subscribeToSaveResponse(
                this.nhanVienService.create(this.nhanVien));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NhanVien>>) {
        result.subscribe((res: HttpResponse<NhanVien>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NhanVien) {
        this.eventManager.broadcast({ name: 'nhanVienListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackXaById(index: number, item: Xa) {
        return item.id;
    }

    trackCuaHangById(index: number, item: CuaHang) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
    filterXas(event: any) {
        const query = event.query;
        console.log(query);
        this.filteredXas = this.filterXa(query, this.huyen.xas);
        // this.xaService.getXa(query).subscribe((xas: any) => {
        //     this.filteredXas = this.filterXa(query, xas);
        // });
    }
    filterXa(query: any, xas: Xa[]): Xa[] {
        const filtered: any[] = [];
        for (const Xa of xas) {
            if (Xa.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Xa);
            }
        }
        return filtered;
    }
    filterTinhs(event: any) {
        const query = event.query;
        console.log(query);
        this.tinhService.getTinh(query).subscribe((tinhs: any) => {
            
            console.log(tinhs);
            // this.filteredTinhs = this.filterTinh(query, tinhs);
            this.filteredTinhs =  tinhs;

        });
    }
    filterTinh(query: any, tinhs: Tinh[]): Tinh[] {
        const filtered: any[] = [];
        for (const Tinh of tinhs) {
            if (Tinh.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Tinh);
            }
        }
        return filtered;
    }
    filterHuyens(event: any) {
        const query = event.query;
        console.log(query);
        this.filteredHuyens =this.filterHuyen(query, this.tinh.huyens);
        // this.huyenService.getHuyenByTinh(query, this.tinh.id).subscribe((huyens: any) => {
        //     this.filteredHuyens = this.filterHuyen(query, huyens);
        // });
    }
    filterHuyen(query: any, huyens: Huyen[]): Huyen[] {
        const filtered: any[] = [];
        for (const Huyen of huyens) {
            if (Huyen.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Huyen);
            }
        }
        return filtered;
    }
}

@Component({
    selector: 'jhi-nhan-vien-popup',
    template: ''
})
export class NhanVienPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nhanVienPopupService: NhanVienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nhanVienPopupService
                    .open(NhanVienDialogComponent as Component, params['id']);
            } else {
                this.nhanVienPopupService
                    .open(NhanVienDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
