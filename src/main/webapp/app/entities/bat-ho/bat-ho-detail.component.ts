import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BatHo } from './bat-ho.model';
import { BatHoService } from './bat-ho.service';

@Component({
    selector: 'jhi-bat-ho-detail',
    templateUrl: './bat-ho-detail.component.html'
})
export class BatHoDetailComponent implements OnInit, OnDestroy {

    batHo: BatHo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBatHos();
    }

    load(id) {
        this.batHoService.find(id)
            .subscribe((batHoResponse: HttpResponse<BatHo>) => {
                this.batHo = batHoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBatHos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'batHoListModification',
            (response) => this.load(this.batHo.id)
        );
    }
}
