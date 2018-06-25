import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Huyen } from './huyen.model';
import { HuyenPopupService } from './huyen-popup.service';
import { HuyenService } from './huyen.service';
import { Tinh, TinhService } from '../tinh';

@Component({
    selector: 'jhi-huyen-dialog',
    templateUrl: './huyen-dialog.component.html'
})
export class HuyenDialogComponent implements OnInit {

    huyen: Huyen;
    isSaving: boolean;

    tinhs: Tinh[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private huyenService: HuyenService,
        private tinhService: TinhService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tinhService.query()
            .subscribe((res: HttpResponse<Tinh[]>) => { this.tinhs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.huyen.id !== undefined) {
            this.subscribeToSaveResponse(
                this.huyenService.update(this.huyen));
        } else {
            this.subscribeToSaveResponse(
                this.huyenService.create(this.huyen));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Huyen>>) {
        result.subscribe((res: HttpResponse<Huyen>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Huyen) {
        this.eventManager.broadcast({ name: 'huyenListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTinhById(index: number, item: Tinh) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-huyen-popup',
    template: ''
})
export class HuyenPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private huyenPopupService: HuyenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.huyenPopupService
                    .open(HuyenDialogComponent as Component, params['id']);
            } else {
                this.huyenPopupService
                    .open(HuyenDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
