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
    selector: 'jhi-gop-von',
    templateUrl: './gop-von.component.html',
    styles: []
})
export class GopVonComponent implements OnInit {
    thuChis: ThuChi[];
    currentAccount: any;
    eventSubscriber: Subscription;
    thuchi: ThuChi;
    tungay: Date;
    denngay: Date;
    isSaving: boolean;
    nhanviens: NhanVien[];
    tongTien: number;

    constructor(
        // public activeModal: NgbActiveModal,
        private thuChiService: ThuChiService,
        private jhiAlertService: JhiAlertService,
        private nhanVienService: NhanVienService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.thuchi = new ThuChi();
        this.tongTien = 0;
    }
    timkiem() {
        // console.log(this.tungay);
        // console.log(this.denngay);
        this.tongTien = 0;
        this.thuChiService
            .findByTime(this.tungay, this.denngay, THUCHI.GOPVON)
            .subscribe(
                (res: HttpResponse<ThuChi[]>) => {
                    this.thuChis = res.body;
                    this.thuChis.forEach(element => {
                        this.tongTien =this.tongTien+ element.sotien;
                        console.log(element.sotien);
                        console.log(this.tongTien);
                        
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    // clear() {
    //     this.activeModal.dismiss('cancel');
    // }
    save() {
        this.thuchi.thuchi = THUCHI.GOPVON;
        this.isSaving = true;
        if (this.thuchi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thuChiService.update(this.thuchi)
            );
        } else {
            this.subscribeToSaveResponse(
                this.thuChiService.create(this.thuchi)
            );
        }
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<ThuChi>>) {
        result.subscribe(
            (res: HttpResponse<ThuChi>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }
    private onSaveSuccess(result: ThuChi) {
        this.eventManager.broadcast({
            name: 'thuChiListModification',
            content: 'OK'
        });
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
        this.loadAll();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    loadAll() {
        // this.thuChiService.query().subscribe(
        //     (res: HttpResponse<ThuChi[]>) => {
        //         this.thuChis = res.body;
        //     },
        //     (res: HttpErrorResponse) => this.onError(res.message)
        // );
        this.tungay = new Date();
        this.denngay = new Date();
        console.log(this.tungay);
        
        this.thuChiService
        .findByTime(this.tungay, this.denngay, THUCHI.GOPVON)
        .subscribe(
            (res: HttpResponse<ThuChi[]>) => {
                this.thuChis = res.body;
                this.thuChis.forEach(element => {
                    this.tongTien =this.tongTien+ element.sotien;
                    console.log(element.sotien);
                    console.log(this.tongTien);
                    
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        //   this.registerChangeInThuChis();
        this.nhanVienService.query().subscribe(
            (res: HttpResponse<NhanVien[]>) => {
                this.nhanviens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnDestroy() {
        //   this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ThuChi) {
        return item.id;
    }
    registerChangeInThuChis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'thuChiListModification',
            response => this.loadAll()
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
