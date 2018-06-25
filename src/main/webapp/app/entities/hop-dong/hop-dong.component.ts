import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDong } from './hop-dong.model';
import { HopDongService } from './hop-dong.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-dong',
    templateUrl: './hop-dong.component.html'
})
export class HopDongComponent implements OnInit, OnDestroy {
hopDongs: HopDong[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hopDongService: HopDongService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hopDongService.query().subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHopDongs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopDong) {
        return item.id;
    }
    registerChangeInHopDongs() {
        this.eventSubscriber = this.eventManager.subscribe('hopDongListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
