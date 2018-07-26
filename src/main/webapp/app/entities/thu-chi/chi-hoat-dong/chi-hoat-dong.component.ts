import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ThuChi, THUCHI } from '../thu-chi.model';
import { ThuChiService } from '../thu-chi.service';
import { Principal } from '../../../shared';
import { Observable } from '../../../../../../../node_modules/rxjs/Observable';
import { NhanVien, NhanVienService } from '../../nhan-vien';
@Component({
    selector: 'jhi-chi-hoat-dong',
    templateUrl: './chi-hoat-dong.component.html',
    styles: []
})
export class ChiHoatDongComponent implements OnInit, OnDestroy {

    thuChis: ThuChi[];
    currentAccount: any;
    eventSubscriber: Subscription;
    tungay: Date;
    denngay: Date;
    thuchi: ThuChi;
    isSaving: boolean;
    nhanviens: NhanVien[];

    constructor(
        private thuChiService: ThuChiService,
        private jhiAlertService: JhiAlertService,
        private nhanVienService: NhanVienService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.thuchi = new ThuChi();
    }
    timkiem() {

            // console.log(this.tungay);
        // console.log(this.denngay);
        this.thuChiService.findByTime(this.tungay, this.denngay, THUCHI.CHI).subscribe(
            (res: HttpResponse<ThuChi[]>) => {
                this.thuChis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }
    save() {
        this.thuchi.thuchi = THUCHI.CHI;
        this.isSaving = true;
        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi));
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.create(this.thuchi));
        }
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<ThuChi>>) {
        result.subscribe(
            (res: HttpResponse<ThuChi>) => this.onSaveSuccess(res.body), 
            (res: HttpErrorResponse) => this.onSaveError());
    }
    private onSaveSuccess(result: ThuChi) {
        this.eventManager.broadcast({ name: 'thuChiListModification', content: 'OK'});
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    loadAll() {
        // this.thuChiService.findAllThuChiTheoLoai(THUCHI.CHI).subscribe(
        //     (res: HttpResponse<ThuChi[]>) => {
        //         this.thuChis = res.body;
        //     },
        //     (res: HttpErrorResponse) => this.onError(res.message)
        // );
        this.tungay = new Date();
        this.denngay = new Date();
        console.log(this.tungay);
        
        this.thuChiService
        .findByTime(this.tungay, this.denngay, THUCHI.CHI)
        .subscribe(
            (res: HttpResponse<ThuChi[]>) => {
                this.thuChis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        // this.registerChangeInThuChis();
        this.nhanVienService.query()
        .subscribe((res: HttpResponse<NhanVien[]>) => { this.nhanviens = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ThuChi) {
        return item.id;
    }
    registerChangeInThuChis() {
        this.eventSubscriber = this.eventManager.subscribe('thuChiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
