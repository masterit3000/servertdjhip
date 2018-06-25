import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { GhiNoPopupService } from './ghi-no-popup.service';
import { GhiNoService } from './ghi-no.service';
import { NhanVien, NhanVienService } from '../nhan-vien';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-ghi-no-dialog',
    templateUrl: './ghi-no-dialog.component.html'
})
export class GhiNoDialogComponent implements OnInit {

    ghiNo: GhiNo;
    isSaving: boolean;

    nhanviens: NhanVien[];

    hopdongs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ghiNoService: GhiNoService,
        private nhanVienService: NhanVienService,
        private hopDongService: HopDongService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.nhanVienService.query()
            .subscribe((res: HttpResponse<NhanVien[]>) => { this.nhanviens = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopDongService.query()
            .subscribe((res: HttpResponse<HopDong[]>) => { this.hopdongs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ghiNoService.update(this.ghiNo));
        } else {
            this.subscribeToSaveResponse(
                this.ghiNoService.create(this.ghiNo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GhiNo>>) {
        result.subscribe((res: HttpResponse<GhiNo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GhiNo) {
        this.eventManager.broadcast({ name: 'ghiNoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackNhanVienById(index: number, item: NhanVien) {
        return item.id;
    }

    trackHopDongById(index: number, item: HopDong) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ghi-no-popup',
    template: ''
})
export class GhiNoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ghiNoPopupService: GhiNoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ghiNoPopupService
                    .open(GhiNoDialogComponent as Component, params['id']);
            } else {
                this.ghiNoPopupService
                    .open(GhiNoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
