import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuDongTien } from './lich-su-dong-tien.model';
import { LichSuDongTienPopupService } from './lich-su-dong-tien-popup.service';
import { LichSuDongTienService } from './lich-su-dong-tien.service';
import { NhanVien, NhanVienService } from '../nhan-vien';
import { HopDong, HopDongService } from '../hop-dong';

@Component({
    selector: 'jhi-lich-su-dong-tien-dialog',
    templateUrl: './lich-su-dong-tien-dialog.component.html'
})
export class LichSuDongTienDialogComponent implements OnInit {

    lichSuDongTien: LichSuDongTien;
    isSaving: boolean;

    nhanviens: NhanVien[];

    hopdongs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lichSuDongTienService: LichSuDongTienService,
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
        if (this.lichSuDongTien.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lichSuDongTienService.update(this.lichSuDongTien));
        } else {
            this.subscribeToSaveResponse(
                this.lichSuDongTienService.create(this.lichSuDongTien));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LichSuDongTien>>) {
        result.subscribe((res: HttpResponse<LichSuDongTien>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LichSuDongTien) {
        this.eventManager.broadcast({ name: 'lichSuDongTienListModification', content: 'OK'});
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
    selector: 'jhi-lich-su-dong-tien-popup',
    template: ''
})
export class LichSuDongTienPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lichSuDongTienPopupService: LichSuDongTienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lichSuDongTienPopupService
                    .open(LichSuDongTienDialogComponent as Component, params['id']);
            } else {
                this.lichSuDongTienPopupService
                    .open(LichSuDongTienDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
