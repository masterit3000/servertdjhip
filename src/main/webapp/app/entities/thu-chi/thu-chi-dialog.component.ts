import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ThuChi } from './thu-chi.model';
import { ThuChiPopupService } from './thu-chi-popup.service';
import { ThuChiService } from './thu-chi.service';
import { CuaHang, CuaHangService } from '../cua-hang';
import { NhanVien, NhanVienService } from '../nhan-vien';

@Component({
    selector: 'jhi-thu-chi-dialog',
    templateUrl: './thu-chi-dialog.component.html'
})
export class ThuChiDialogComponent implements OnInit {

    thuChi: ThuChi;
    isSaving: boolean;

    cuahangs: CuaHang[];

    nhanviens: NhanVien[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private thuChiService: ThuChiService,
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
        if (this.thuChi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuChi));
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.create(this.thuChi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ThuChi>>) {
        result.subscribe((res: HttpResponse<ThuChi>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ThuChi) {
        this.eventManager.broadcast({ name: 'thuChiListModification', content: 'OK'});
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
    selector: 'jhi-thu-chi-popup',
    template: ''
})
export class ThuChiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thuChiPopupService: ThuChiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.thuChiPopupService
                    .open(ThuChiDialogComponent as Component, params['id']);
            } else {
                this.thuChiPopupService
                    .open(ThuChiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
