import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TaiSan } from './tai-san.model';
import { TaiSanService } from './tai-san.service';

@Component({
    selector: 'jhi-tai-san-detail',
    templateUrl: './tai-san-detail.component.html'
})
export class TaiSanDetailComponent implements OnInit, OnDestroy {

    taiSan: TaiSan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private taiSanService: TaiSanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaiSans();
    }

    load(id) {
        this.taiSanService.find(id)
            .subscribe((taiSanResponse: HttpResponse<TaiSan>) => {
                this.taiSan = taiSanResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaiSans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'taiSanListModification',
            (response) => this.load(this.taiSan.id)
        );
    }
}
