import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tinh } from './tinh.model';
import { TinhPopupService } from './tinh-popup.service';
import { TinhService } from './tinh.service';

@Component({
    selector: 'jhi-tinh-dialog',
    templateUrl: './tinh-dialog.component.html'
})
export class TinhDialogComponent implements OnInit {

    tinh: Tinh;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tinhService: TinhService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tinh.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tinhService.update(this.tinh));
        } else {
            this.subscribeToSaveResponse(
                this.tinhService.create(this.tinh));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tinh>>) {
        result.subscribe((res: HttpResponse<Tinh>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tinh) {
        this.eventManager.broadcast({ name: 'tinhListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tinh-popup',
    template: ''
})
export class TinhPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tinhPopupService: TinhPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tinhPopupService
                    .open(TinhDialogComponent as Component, params['id']);
            } else {
                this.tinhPopupService
                    .open(TinhDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
