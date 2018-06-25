import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VayLai } from './vay-lai.model';
import { VayLaiService } from './vay-lai.service';

@Component({
    selector: 'jhi-vay-lai-detail',
    templateUrl: './vay-lai-detail.component.html'
})
export class VayLaiDetailComponent implements OnInit, OnDestroy {

    vayLai: VayLai;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vayLaiService: VayLaiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVayLais();
    }

    load(id) {
        this.vayLaiService.find(id)
            .subscribe((vayLaiResponse: HttpResponse<VayLai>) => {
                this.vayLai = vayLaiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVayLais() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vayLaiListModification',
            (response) => this.load(this.vayLai.id)
        );
    }
}
