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

@Component({
    selector: 'jhi-nhan-vien-dialog',
    templateUrl: './nhan-vien-dialog.component.html'
})
export class NhanVienDialogComponent implements OnInit {

    nhanVien: NhanVien;
    isSaving: boolean;

    xas: Xa[];

    cuahangs: CuaHang[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private nhanVienService: NhanVienService,
        private xaService: XaService,
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
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
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
