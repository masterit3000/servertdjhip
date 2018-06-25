import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tinh } from './tinh.model';
import { TinhService } from './tinh.service';

@Component({
    selector: 'jhi-tinh-detail',
    templateUrl: './tinh-detail.component.html'
})
export class TinhDetailComponent implements OnInit, OnDestroy {

    tinh: Tinh;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tinhService: TinhService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTinhs();
    }

    load(id) {
        this.tinhService.find(id)
            .subscribe((tinhResponse: HttpResponse<Tinh>) => {
                this.tinh = tinhResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTinhs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tinhListModification',
            (response) => this.load(this.tinh.id)
        );
    }
}
