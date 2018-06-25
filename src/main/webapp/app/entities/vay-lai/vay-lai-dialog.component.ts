import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VayLai } from './vay-lai.model';
import { VayLaiPopupService } from './vay-lai-popup.service';
import { VayLaiService } from './vay-lai.service';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-vay-lai-dialog',
    templateUrl: './vay-lai-dialog.component.html'
})
export class VayLaiDialogComponent implements OnInit {

    vayLai: VayLai;
    isSaving: boolean;

    hopdongvls: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private vayLaiService: VayLaiService,
        private hopDongService: HopDongService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDongService
            .query({filter: 'vaylai-is-null'})
            .subscribe((res: HttpResponse<HopDong[]>) => {
                if (!this.vayLai.hopdongvlId) {
                    this.hopdongvls = res.body;
                } else {
                    this.hopDongService
                        .find(this.vayLai.hopdongvlId)
                        .subscribe((subRes: HttpResponse<HopDong>) => {
                            this.hopdongvls = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vayLai.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vayLaiService.update(this.vayLai));
        } else {
            this.subscribeToSaveResponse(
                this.vayLaiService.create(this.vayLai));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VayLai>>) {
        result.subscribe((res: HttpResponse<VayLai>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VayLai) {
        this.eventManager.broadcast({ name: 'vayLaiListModification', content: 'OK'});
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
    selector: 'jhi-vay-lai-popup',
    template: ''
})
export class VayLaiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vayLaiPopupService: VayLaiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vayLaiPopupService
                    .open(VayLaiDialogComponent as Component, params['id']);
            } else {
                this.vayLaiPopupService
                    .open(VayLaiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
