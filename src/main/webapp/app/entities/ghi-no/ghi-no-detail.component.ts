import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { GhiNoService } from './ghi-no.service';

@Component({
    selector: 'jhi-ghi-no-detail',
    templateUrl: './ghi-no-detail.component.html'
})
export class GhiNoDetailComponent implements OnInit, OnDestroy {

    ghiNo: GhiNo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ghiNoService: GhiNoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGhiNos();
    }

    load(id) {
        this.ghiNoService.find(id)
            .subscribe((ghiNoResponse: HttpResponse<GhiNo>) => {
                this.ghiNo = ghiNoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGhiNos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ghiNoListModification',
            (response) => this.load(this.ghiNo.id)
        );
    }
}
