import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnhTaiSan } from './anh-tai-san.model';
import { AnhTaiSanService } from './anh-tai-san.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-anh-tai-san',
    templateUrl: './anh-tai-san.component.html'
})
export class AnhTaiSanComponent implements OnInit, OnDestroy {
anhTaiSans: AnhTaiSan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private anhTaiSanService: AnhTaiSanService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.anhTaiSanService.query().subscribe(
            (res: HttpResponse<AnhTaiSan[]>) => {
                this.anhTaiSans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnhTaiSans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AnhTaiSan) {
        return item.id;
    }
    registerChangeInAnhTaiSans() {
        this.eventSubscriber = this.eventManager.subscribe('anhTaiSanListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
