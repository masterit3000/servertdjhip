import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BatHo } from '../../bat-ho/bat-ho.model';
import { BatHoService } from '../../bat-ho/bat-ho.service';
import { Principal } from '../../../shared';
import { TRANGTHAIHOPDONG } from '../../hop-dong';

@Component({
    selector: 'bat-ho-admin',
    templateUrl: './bat-ho.component.html'
})
export class BatHoAdminComponent implements OnInit, OnDestroy {
    batHos: BatHo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    text: any;
    selected: BatHo;
    none: any;
    keyTimBatHo: string;
    loaihopdong: any;
    constructor(
        private batHoService: BatHoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.loaihopdong = 1;
    }

    loadAll() {
        if (this.loaihopdong == 1) {

            this.batHoService.query(TRANGTHAIHOPDONG.DANGVAY).subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        } else {
            console.log("chon duoc")
            this.batHoService.query(TRANGTHAIHOPDONG.DADONG).subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }

    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBatHos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BatHo) {
        return item.id;
    }
    registerChangeInBatHos() {
        this.eventSubscriber = this.eventManager.subscribe('batHoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    timBatHo() {
        if (this.loaihopdong == 1) {
            this.batHoService.findBatHoByTenOrCMND(this.keyTimBatHo, TRANGTHAIHOPDONG.DANGVAY).subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        } else {
            this.batHoService.findBatHoByTenOrCMND(this.keyTimBatHo, TRANGTHAIHOPDONG.DADONG).subscribe(
                (res: HttpResponse<BatHo[]>) => {
                    this.batHos = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

}
