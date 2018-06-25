import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDong } from './hop-dong.model';
import { HopDongPopupService } from './hop-dong-popup.service';
import { HopDongService } from './hop-dong.service';
import { KhachHang, KhachHangService } from '../khach-hang';
import { CuaHang, CuaHangService } from '../cua-hang';
import { NhanVien, NhanVienService } from '../nhan-vien';

@Component({
    selector: 'jhi-hop-dong-dialog',
    templateUrl: './hop-dong-dialog.component.html'
})
export class HopDongDialogComponent implements OnInit {

    hopDong: HopDong;
    isSaving: boolean;

    khachhangs: KhachHang[];

    cuahangs: CuaHang[];

    nhanviens: NhanVien[];

    hopdonggocs: HopDong[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopDongService: HopDongService,
        private khachHangService: KhachHangService,
        private cuaHangService: CuaHangService,
        private nhanVienService: NhanVienService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.khachHangService.query()
            .subscribe((res: HttpResponse<KhachHang[]>) => { this.khachhangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cuaHangService.query()
            .subscribe((res: HttpResponse<CuaHang[]>) => { this.cuahangs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.nhanVienService.query()
            .subscribe((res: HttpResponse<NhanVien[]>) => { this.nhanviens = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopDongService
            .query({filter: 'hopdong-is-null'})
            .subscribe((res: HttpResponse<HopDong[]>) => {
                if (!this.hopDong.hopdonggocId) {
                    this.hopdonggocs = res.body;
                } else {
                    this.hopDongService
                        .find(this.hopDong.hopdonggocId)
                        .subscribe((subRes: HttpResponse<HopDong>) => {
                            this.hopdonggocs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hopDong.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopDongService.update(this.hopDong));
        } else {
            this.subscribeToSaveResponse(
                this.hopDongService.create(this.hopDong));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopDong>>) {
        result.subscribe((res: HttpResponse<HopDong>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopDong) {
        this.eventManager.broadcast({ name: 'hopDongListModification', content: 'OK'});
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

    trackCuaHangById(index: number, item: CuaHang) {
        return item.id;
    }

    trackNhanVienById(index: number, item: NhanVien) {
        return item.id;
    }

    trackHopDongById(index: number, item: HopDong) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hop-dong-popup',
    template: ''
})
export class HopDongPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDongPopupService: HopDongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopDongPopupService
                    .open(HopDongDialogComponent as Component, params['id']);
            } else {
                this.hopDongPopupService
                    .open(HopDongDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
