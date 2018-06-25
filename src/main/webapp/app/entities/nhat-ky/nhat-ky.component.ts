import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NhatKy } from './nhat-ky.model';
import { NhatKyService } from './nhat-ky.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-nhat-ky',
    templateUrl: './nhat-ky.component.html'
})
export class NhatKyComponent implements OnInit, OnDestroy {
nhatKies: NhatKy[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nhatKyService: NhatKyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nhatKyService.query().subscribe(
            (res: HttpResponse<NhatKy[]>) => {
                this.nhatKies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNhatKies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NhatKy) {
        return item.id;
    }
    registerChangeInNhatKies() {
        this.eventSubscriber = this.eventManager.subscribe('nhatKyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
