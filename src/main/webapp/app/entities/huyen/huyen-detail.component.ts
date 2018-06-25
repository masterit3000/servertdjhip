import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Huyen } from './huyen.model';
import { HuyenService } from './huyen.service';

@Component({
    selector: 'jhi-huyen-detail',
    templateUrl: './huyen-detail.component.html'
})
export class HuyenDetailComponent implements OnInit, OnDestroy {

    huyen: Huyen;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private huyenService: HuyenService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHuyens();
    }

    load(id) {
        this.huyenService.find(id)
            .subscribe((huyenResponse: HttpResponse<Huyen>) => {
                this.huyen = huyenResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHuyens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'huyenListModification',
            (response) => this.load(this.huyen.id)
        );
    }
}
