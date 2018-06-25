import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { CuaHangPopupService } from './cua-hang-popup.service';
import { CuaHangService } from './cua-hang.service';
import { Xa, XaService } from '../xa';

@Component({
    selector: 'jhi-cua-hang-dialog',
    templateUrl: './cua-hang-dialog.component.html'
})
export class CuaHangDialogComponent implements OnInit {

    cuaHang: CuaHang;
    isSaving: boolean;

    xas: Xa[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cuaHangService: CuaHangService,
        private xaService: XaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.xaService.query()
            .subscribe((res: HttpResponse<Xa[]>) => { this.xas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cuaHang.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cuaHangService.update(this.cuaHang));
        } else {
            this.subscribeToSaveResponse(
                this.cuaHangService.create(this.cuaHang));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CuaHang>>) {
        result.subscribe((res: HttpResponse<CuaHang>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CuaHang) {
        this.eventManager.broadcast({ name: 'cuaHangListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-cua-hang-popup',
    template: ''
})
export class CuaHangPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cuaHangPopupService: CuaHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cuaHangPopupService
                    .open(CuaHangDialogComponent as Component, params['id']);
            } else {
                this.cuaHangPopupService
                    .open(CuaHangDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
