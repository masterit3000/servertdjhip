import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NhatKy } from './nhat-ky.model';
import { NhatKyService } from './nhat-ky.service';

@Component({
    selector: 'jhi-nhat-ky-detail',
    templateUrl: './nhat-ky-detail.component.html'
})
export class NhatKyDetailComponent implements OnInit, OnDestroy {

    nhatKy: NhatKy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nhatKyService: NhatKyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNhatKies();
    }

    load(id) {
        this.nhatKyService.find(id)
            .subscribe((nhatKyResponse: HttpResponse<NhatKy>) => {
                this.nhatKy = nhatKyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNhatKies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nhatKyListModification',
            (response) => this.load(this.nhatKy.id)
        );
    }
}
