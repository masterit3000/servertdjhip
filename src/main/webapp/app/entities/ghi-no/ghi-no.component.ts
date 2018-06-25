import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { GhiNoService } from './ghi-no.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-ghi-no',
    templateUrl: './ghi-no.component.html'
})
export class GhiNoComponent implements OnInit, OnDestroy {
ghiNos: GhiNo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ghiNoService: GhiNoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ghiNoService.query().subscribe(
            (res: HttpResponse<GhiNo[]>) => {
                this.ghiNos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGhiNos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GhiNo) {
        return item.id;
    }
    registerChangeInGhiNos() {
        this.eventSubscriber = this.eventManager.subscribe('ghiNoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
