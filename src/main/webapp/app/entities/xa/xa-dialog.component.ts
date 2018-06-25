import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Xa } from './xa.model';
import { XaPopupService } from './xa-popup.service';
import { XaService } from './xa.service';
import { Huyen, HuyenService } from '../huyen';

@Component({
    selector: 'jhi-xa-dialog',
    templateUrl: './xa-dialog.component.html'
})
export class XaDialogComponent implements OnInit {

    xa: Xa;
    isSaving: boolean;

    huyens: Huyen[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private xaService: XaService,
        private huyenService: HuyenService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.huyenService.query()
            .subscribe((res: HttpResponse<Huyen[]>) => { this.huyens = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.xa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.xaService.update(this.xa));
        } else {
            this.subscribeToSaveResponse(
                this.xaService.create(this.xa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Xa>>) {
        result.subscribe((res: HttpResponse<Xa>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Xa) {
        this.eventManager.broadcast({ name: 'xaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHuyenById(index: number, item: Huyen) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-xa-popup',
    template: ''
})
export class XaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private xaPopupService: XaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.xaPopupService
                    .open(XaDialogComponent as Component, params['id']);
            } else {
                this.xaPopupService
                    .open(XaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
