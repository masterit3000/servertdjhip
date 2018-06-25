import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopDong } from './hop-dong.model';
import { HopDongService } from './hop-dong.service';

@Component({
    selector: 'jhi-hop-dong-detail',
    templateUrl: './hop-dong-detail.component.html'
})
export class HopDongDetailComponent implements OnInit, OnDestroy {

    hopDong: HopDong;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopDongService: HopDongService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopDongs();
    }

    load(id) {
        this.hopDongService.find(id)
            .subscribe((hopDongResponse: HttpResponse<HopDong>) => {
                this.hopDong = hopDongResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopDongs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopDongListModification',
            (response) => this.load(this.hopDong.id)
        );
    }
}
