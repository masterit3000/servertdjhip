import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NhatKy } from './nhat-ky.model';
import { NhatKyPopupService } from './nhat-ky-popup.service';
import { NhatKyService } from './nhat-ky.service';
import { CuaHang, CuaHangService } from '../cua-hang';
import { NhanVien, NhanVienService } from '../nhan-vien';

@Component({
    selector: 'jhi-nhat-ky-dialog',
    templateUrl: './nhat-ky-dialog.component.html'
})
export class NhatKyDialogComponent implements OnInit {

    nhatKy: NhatKy;
    isSaving: boolean;

    cuahangs: CuaHang[];

    nhanviens: NhanVien[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private nhatKyService: NhatKyService,
        private cuaHangService: CuaHangService,
        private nhanVienService: NhanVienService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cuaHangService.query()
            .subscribe((res: HttpResponse<CuaHang[]>) => { this.cuahangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.nhanVienService.query()
            .subscribe((res: HttpResponse<NhanVien[]>) => { this.nhanviens = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.nhatKy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nhatKyService.update(this.nhatKy));
        } else {
            this.subscribeToSaveResponse(
                this.nhatKyService.create(this.nhatKy));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NhatKy>>) {
        result.subscribe((res: HttpResponse<NhatKy>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NhatKy) {
        this.eventManager.broadcast({ name: 'nhatKyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCuaHangById(index: number, item: CuaHang) {
        return item.id;
    }

    trackNhanVienById(index: number, item: NhanVien) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-nhat-ky-popup',
    template: ''
})
export class NhatKyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nhatKyPopupService: NhatKyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nhatKyPopupService
                    .open(NhatKyDialogComponent as Component, params['id']);
            } else {
                this.nhatKyPopupService
                    .open(NhatKyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
