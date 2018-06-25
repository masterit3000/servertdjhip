import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tinh } from './tinh.model';
import { TinhService } from './tinh.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tinh',
    templateUrl: './tinh.component.html'
})
export class TinhComponent implements OnInit, OnDestroy {
tinhs: Tinh[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tinhService: TinhService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tinhService.query().subscribe(
            (res: HttpResponse<Tinh[]>) => {
                this.tinhs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTinhs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tinh) {
        return item.id;
    }
    registerChangeInTinhs() {
        this.eventSubscriber = this.eventManager.subscribe('tinhListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
