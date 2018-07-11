import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ThuChi } from './thu-chi.model';
import { ThuChiService } from './thu-chi.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-thu-chi',
    templateUrl: './thu-chi.component.html'
})
export class ThuChiComponent implements OnInit, OnDestroy {
thuChis: ThuChi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private thuChiService: ThuChiService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.thuChiService.query().subscribe(
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
        this.registerChangeInThuChis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
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
