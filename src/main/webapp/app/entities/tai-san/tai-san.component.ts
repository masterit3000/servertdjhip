import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaiSan } from './tai-san.model';
import { TaiSanService } from './tai-san.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tai-san',
    templateUrl: './tai-san.component.html'
})
export class TaiSanComponent implements OnInit, OnDestroy {
taiSans: TaiSan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private taiSanService: TaiSanService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.taiSanService.query().subscribe(
            (res: HttpResponse<TaiSan[]>) => {
                this.taiSans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTaiSans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TaiSan) {
        return item.id;
    }
    registerChangeInTaiSans() {
        this.eventSubscriber = this.eventManager.subscribe('taiSanListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
