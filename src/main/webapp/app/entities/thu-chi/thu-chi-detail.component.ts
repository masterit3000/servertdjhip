import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ThuChi } from './thu-chi.model';
import { ThuChiService } from './thu-chi.service';

@Component({
    selector: 'jhi-thu-chi-detail',
    templateUrl: './thu-chi-detail.component.html'
})
export class ThuChiDetailComponent implements OnInit, OnDestroy {

    thuChi: ThuChi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private thuChiService: ThuChiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInThuChis();
    }

    load(id) {
        this.thuChiService.find(id)
            .subscribe((thuChiResponse: HttpResponse<ThuChi>) => {
                this.thuChi = thuChiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInThuChis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'thuChiListModification',
            (response) => this.load(this.thuChi.id)
        );
    }
}
