import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { CuaHangService } from './cua-hang.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-cua-hang',
    templateUrl: './cua-hang.component.html'
})
export class CuaHangComponent implements OnInit, OnDestroy {
cuaHangs: CuaHang[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cuaHangService: CuaHangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cuaHangService.query().subscribe(
            (res: HttpResponse<CuaHang[]>) => {
                this.cuaHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCuaHangs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CuaHang) {
        return item.id;
    }
    registerChangeInCuaHangs() {
        this.eventSubscriber = this.eventManager.subscribe('cuaHangListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
