import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaiSan } from './tai-san.model';
import { TaiSanPopupService } from './tai-san-popup.service';
import { TaiSanService } from './tai-san.service';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-tai-san-dialog',
    templateUrl: './tai-san-dialog.component.html'
})
export class TaiSanDialogComponent implements OnInit {

    taiSan: TaiSan;
    isSaving: boolean;

    hopdongs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private taiSanService: TaiSanService,
        private hopDongService: HopDongService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDongService.query()
            .subscribe((res: HttpResponse<HopDong[]>) => { this.hopdongs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.taiSan.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taiSanService.update(this.taiSan));
        } else {
            this.subscribeToSaveResponse(
                this.taiSanService.create(this.taiSan));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TaiSan>>) {
        result.subscribe((res: HttpResponse<TaiSan>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TaiSan) {
        this.eventManager.broadcast({ name: 'taiSanListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHopDongById(index: number, item: HopDong) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tai-san-popup',
    template: ''
})
export class TaiSanPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taiSanPopupService: TaiSanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.taiSanPopupService
                    .open(TaiSanDialogComponent as Component, params['id']);
            } else {
                this.taiSanPopupService
                    .open(TaiSanDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
