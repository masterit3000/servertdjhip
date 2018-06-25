import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Xa } from './xa.model';
import { XaService } from './xa.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-xa',
    templateUrl: './xa.component.html'
})
export class XaComponent implements OnInit, OnDestroy {
xas: Xa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private xaService: XaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.xaService.query().subscribe(
            (res: HttpResponse<Xa[]>) => {
                this.xas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInXas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Xa) {
        return item.id;
    }
    registerChangeInXas() {
        this.eventSubscriber = this.eventManager.subscribe('xaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
