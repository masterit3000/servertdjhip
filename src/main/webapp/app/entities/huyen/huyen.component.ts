import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Huyen } from './huyen.model';
import { HuyenService } from './huyen.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-huyen',
    templateUrl: './huyen.component.html'
})
export class HuyenComponent implements OnInit, OnDestroy {
huyens: Huyen[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private huyenService: HuyenService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.huyenService.query().subscribe(
            (res: HttpResponse<Huyen[]>) => {
                this.huyens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHuyens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Huyen) {
        return item.id;
    }
    registerChangeInHuyens() {
        this.eventSubscriber = this.eventManager.subscribe('huyenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
