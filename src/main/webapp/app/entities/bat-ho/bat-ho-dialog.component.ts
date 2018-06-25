import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BatHo } from './bat-ho.model';
import { BatHoPopupService } from './bat-ho-popup.service';
import { BatHoService } from './bat-ho.service';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-bat-ho-dialog',
    templateUrl: './bat-ho-dialog.component.html'
})
export class BatHoDialogComponent implements OnInit {

    batHo: BatHo;
    isSaving: boolean;

    hopdongbhs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private batHoService: BatHoService,
        private hopDongService: HopDongService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDongService
            .query({filter: 'batho-is-null'})
            .subscribe((res: HttpResponse<HopDong[]>) => {
                if (!this.batHo.hopdongbhId) {
                    this.hopdongbhs = res.body;
                } else {
                    this.hopDongService
                        .find(this.batHo.hopdongbhId)
                        .subscribe((subRes: HttpResponse<HopDong>) => {
                            this.hopdongbhs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.batHo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.batHoService.update(this.batHo));
        } else {
            this.subscribeToSaveResponse(
                this.batHoService.create(this.batHo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BatHo>>) {
        result.subscribe((res: HttpResponse<BatHo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BatHo) {
        this.eventManager.broadcast({ name: 'batHoListModification', content: 'OK'});
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
    selector: 'jhi-bat-ho-popup',
    template: ''
})
export class BatHoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private batHoPopupService: BatHoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.batHoPopupService
                    .open(BatHoDialogComponent as Component, params['id']);
            } else {
                this.batHoPopupService
                    .open(BatHoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
