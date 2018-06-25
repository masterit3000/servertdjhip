import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Xa } from './xa.model';
import { XaService } from './xa.service';

@Component({
    selector: 'jhi-xa-detail',
    templateUrl: './xa-detail.component.html'
})
export class XaDetailComponent implements OnInit, OnDestroy {

    xa: Xa;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private xaService: XaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInXas();
    }

    load(id) {
        this.xaService.find(id)
            .subscribe((xaResponse: HttpResponse<Xa>) => {
                this.xa = xaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInXas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'xaListModification',
            (response) => this.load(this.xa.id)
        );
    }
}
