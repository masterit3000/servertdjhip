import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnhTaiSan } from './anh-tai-san.model';
import { AnhTaiSanPopupService } from './anh-tai-san-popup.service';
import { AnhTaiSanService } from './anh-tai-san.service';
import { TaiSan, TaiSanService } from '../tai-san';

@Component({
    selector: 'jhi-anh-tai-san-dialog',
    templateUrl: './anh-tai-san-dialog.component.html'
})
export class AnhTaiSanDialogComponent implements OnInit {

    anhTaiSan: AnhTaiSan;
    isSaving: boolean;

    taisans: TaiSan[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private anhTaiSanService: AnhTaiSanService,
        private taiSanService: TaiSanService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.taiSanService.query()
            .subscribe((res: HttpResponse<TaiSan[]>) => { this.taisans = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.anhTaiSan.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anhTaiSanService.update(this.anhTaiSan));
        } else {
            this.subscribeToSaveResponse(
                this.anhTaiSanService.create(this.anhTaiSan));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AnhTaiSan>>) {
        result.subscribe((res: HttpResponse<AnhTaiSan>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AnhTaiSan) {
        this.eventManager.broadcast({ name: 'anhTaiSanListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTaiSanById(index: number, item: TaiSan) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-anh-tai-san-popup',
    template: ''
})
export class AnhTaiSanPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anhTaiSanPopupService: AnhTaiSanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.anhTaiSanPopupService
                    .open(AnhTaiSanDialogComponent as Component, params['id']);
            } else {
                this.anhTaiSanPopupService
                    .open(AnhTaiSanDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
