import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { LichSuThaoTacHopDongPopupService } from './lich-su-thao-tac-hop-dong-popup.service';
import { LichSuThaoTacHopDongService } from './lich-su-thao-tac-hop-dong.service';
import { NhanVien, NhanVienService } from '../nhan-vien';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-lich-su-thao-tac-hop-dong-dialog',
    templateUrl: './lich-su-thao-tac-hop-dong-dialog.component.html'
})
export class LichSuThaoTacHopDongDialogComponent implements OnInit {

    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    isSaving: boolean;

    nhanviens: NhanVien[];

    hopdongs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
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
        if (this.lichSuThaoTacHopDong.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lichSuThaoTacHopDongService.update(this.lichSuThaoTacHopDong));
        } else {
            this.subscribeToSaveResponse(
                this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LichSuThaoTacHopDong>>) {
        result.subscribe((res: HttpResponse<LichSuThaoTacHopDong>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LichSuThaoTacHopDong) {
        this.eventManager.broadcast({ name: 'lichSuThaoTacHopDongListModification', content: 'OK'});
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
    selector: 'jhi-lich-su-thao-tac-hop-dong-popup',
    template: ''
})
export class LichSuThaoTacHopDongPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lichSuThaoTacHopDongPopupService: LichSuThaoTacHopDongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lichSuThaoTacHopDongPopupService
                    .open(LichSuThaoTacHopDongDialogComponent as Component, params['id']);
            } else {
                this.lichSuThaoTacHopDongPopupService
                    .open(LichSuThaoTacHopDongDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
