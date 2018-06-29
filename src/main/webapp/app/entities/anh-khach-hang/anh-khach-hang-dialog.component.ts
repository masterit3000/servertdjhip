import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnhKhachHang } from './anh-khach-hang.model';
import { AnhKhachHangPopupService } from './anh-khach-hang-popup.service';
import { AnhKhachHangService } from './anh-khach-hang.service';
import { KhachHang, KhachHangService } from '../khach-hang';
import { PlatformLocation } from '@angular/common';

@Component({
    selector: 'jhi-anh-khach-hang-dialog',
    templateUrl: './anh-khach-hang-dialog.component.html'
})
export class AnhKhachHangDialogComponent implements OnInit {

    anhKhachHang: AnhKhachHang;
    isSaving: boolean;

    khachhangs: KhachHang[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private anhKhachHangService: AnhKhachHangService,
        private khachHangService: KhachHangService,
        private eventManager: JhiEventManager,
        location: PlatformLocation
    ) {
        location.onPopState(() => {

            console.log('pressed back!');

            // example for a simple check if modal is opened
            if (this.activeModal !== undefined) {
                console.log('modal is opened - cancel default browser event and close modal');
                // TODO: cancel the default event

                // close modal
                this.activeModal.close();
            }
            else {
                console.log('modal is not opened - default browser event');
            }
        });
    }

    ngOnInit() {
        this.isSaving = false;
        this.khachHangService.query()
            .subscribe((res: HttpResponse<KhachHang[]>) => { this.khachhangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.anhKhachHang.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anhKhachHangService.update(this.anhKhachHang));
        } else {
            this.subscribeToSaveResponse(
                this.anhKhachHangService.create(this.anhKhachHang));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AnhKhachHang>>) {
        result.subscribe((res: HttpResponse<AnhKhachHang>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AnhKhachHang) {
        this.eventManager.broadcast({ name: 'anhKhachHangListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackKhachHangById(index: number, item: KhachHang) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-anh-khach-hang-popup',
    template: ''
})
export class AnhKhachHangPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anhKhachHangPopupService: AnhKhachHangPopupService
    ) {

        
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.anhKhachHangPopupService
                    .open(AnhKhachHangDialogComponent as Component, params['id']);
            } else {
                this.anhKhachHangPopupService
                    .open(AnhKhachHangDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
