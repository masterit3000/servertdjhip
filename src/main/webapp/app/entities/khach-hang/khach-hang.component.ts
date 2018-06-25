import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { KhachHangService } from './khach-hang.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-khach-hang',
    templateUrl: './khach-hang.component.html'
})
export class KhachHangComponent implements OnInit, OnDestroy {
khachHangs: KhachHang[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private khachHangService: KhachHangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.khachHangService.query().subscribe(
            (res: HttpResponse<KhachHang[]>) => {
                this.khachHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKhachHangs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KhachHang) {
        return item.id;
    }
    registerChangeInKhachHangs() {
        this.eventSubscriber = this.eventManager.subscribe('khachHangListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
