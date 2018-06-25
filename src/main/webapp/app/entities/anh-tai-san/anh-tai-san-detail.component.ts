import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AnhTaiSan } from './anh-tai-san.model';
import { AnhTaiSanService } from './anh-tai-san.service';

@Component({
    selector: 'jhi-anh-tai-san-detail',
    templateUrl: './anh-tai-san-detail.component.html'
})
export class AnhTaiSanDetailComponent implements OnInit, OnDestroy {

    anhTaiSan: AnhTaiSan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private anhTaiSanService: AnhTaiSanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnhTaiSans();
    }

    load(id) {
        this.anhTaiSanService.find(id)
            .subscribe((anhTaiSanResponse: HttpResponse<AnhTaiSan>) => {
                this.anhTaiSan = anhTaiSanResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnhTaiSans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anhTaiSanListModification',
            (response) => this.load(this.anhTaiSan.id)
        );
    }
}
