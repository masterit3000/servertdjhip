import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VayLai } from '../../../vay-lai/vay-lai.model';
import { VayLaiService } from '../../../vay-lai/vay-lai.service';
import { BatHo } from '../../../bat-ho/bat-ho.model';
import { BatHoService } from '../../../bat-ho/bat-ho.service';
import { NhanVien } from '../../../nhan-vien/nhan-vien.model';
import { NhanVienService } from '../../../nhan-vien/nhan-vien.service';
import { Principal } from '../../../../shared';

@Component({
    selector: 'nhan-vien-detail-admin',
    templateUrl: './nhan-vien-detail.component.html'
})
export class NhanVienDetailAdminComponent implements OnInit, OnDestroy {

    nhanVien: NhanVien;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    batHos: BatHo[];
    vayLais: VayLai[];
    currentAccount: any;
    text: any;
    selected: BatHo;
    none: any;
    keyTimBatHo: string;
    keyTimVayLai:string;
    constructor(
        private eventManager: JhiEventManager,
        private nhanVienService: NhanVienService,
        private batHoService: BatHoService,
        private vayLaiService: VayLaiService,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private route: ActivatedRoute
    ) {
    }


    timBatHo() {
        // const query = event.query;
        // console.log(query);
        this.batHoService
            .findBatHoByTenOrCMND(this.keyTimBatHo)
            .subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNhanViens();

    }

    load(id) {
        this.nhanVienService.find(id)
            .subscribe((nhanVienResponse: HttpResponse<NhanVien>) => {
                this.nhanVien = nhanVienResponse.body;
            });
            this.batHoService.findByNhanVien(id).subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.vayLaiService.getAllByNhanVien(id).subscribe(
                (res: HttpResponse<VayLai[]>) => {
                    this.vayLais = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackIdBH(index: number, item: BatHo) {
        return item.id;
    }
    trackIdVL(index: number, item: VayLai) {
        return item.id;
    }
    registerChangeInNhanViens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nhanVienListModification',
            (response) => this.load(this.nhanVien.id)
        );
    }
    timVayLai() {
        this.vayLaiService
            .findVayLaiByTenOrCMND(this.keyTimVayLai)
            .subscribe(
                (res: HttpResponse<VayLai[]>) => {
                    this.vayLais = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
