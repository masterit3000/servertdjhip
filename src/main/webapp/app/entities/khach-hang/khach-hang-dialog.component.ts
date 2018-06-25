import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { KhachHangPopupService } from './khach-hang-popup.service';
import { KhachHangService } from './khach-hang.service';
import { Xa, XaService } from '../xa';
import { CuaHang, CuaHangService } from '../cua-hang';

@Component({
    selector: 'jhi-khach-hang-dialog',
    templateUrl: './khach-hang-dialog.component.html'
})
export class KhachHangDialogComponent implements OnInit {

    khachHang: KhachHang;
    isSaving: boolean;

    xas: Xa[];

    cuahangs: CuaHang[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private khachHangService: KhachHangService,
        private xaService: XaService,
        private cuaHangService: CuaHangService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.xaService.query()
            .subscribe((res: HttpResponse<Xa[]>) => { this.xas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cuaHangService.query()
            .subscribe((res: HttpResponse<CuaHang[]>) => { this.cuahangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.khachHang.id !== undefined) {
            this.subscribeToSaveResponse(
                this.khachHangService.update(this.khachHang));
        } else {
            this.subscribeToSaveResponse(
                this.khachHangService.create(this.khachHang));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<KhachHang>>) {
        result.subscribe((res: HttpResponse<KhachHang>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: KhachHang) {
        this.eventManager.broadcast({ name: 'khachHangListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-khach-hang-popup',
    template: ''
})
export class KhachHangPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private khachHangPopupService: KhachHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.khachHangPopupService
                    .open(KhachHangDialogComponent as Component, params['id']);
            } else {
                this.khachHangPopupService
                    .open(KhachHangDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
