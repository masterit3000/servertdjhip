import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VayLai } from './vay-lai.model';
import { VayLaiService } from './vay-lai.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-vay-lai',
    templateUrl: './vay-lai.component.html'
})
export class VayLaiComponent implements OnInit, OnDestroy {
vayLais: VayLai[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vayLaiService: VayLaiService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.vayLaiService.query().subscribe(
            (res: HttpResponse<VayLai[]>) => {
                this.vayLais = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVayLais();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VayLai) {
        return item.id;
    }
    registerChangeInVayLais() {
        this.eventSubscriber = this.eventManager.subscribe('vayLaiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
