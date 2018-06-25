import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LichSuDongTien } from './lich-su-dong-tien.model';
import { LichSuDongTienService } from './lich-su-dong-tien.service';

@Component({
    selector: 'jhi-lich-su-dong-tien-detail',
    templateUrl: './lich-su-dong-tien-detail.component.html'
})
export class LichSuDongTienDetailComponent implements OnInit, OnDestroy {

    lichSuDongTien: LichSuDongTien;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lichSuDongTienService: LichSuDongTienService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLichSuDongTiens();
    }

    load(id) {
        this.lichSuDongTienService.find(id)
            .subscribe((lichSuDongTienResponse: HttpResponse<LichSuDongTien>) => {
                this.lichSuDongTien = lichSuDongTienResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLichSuDongTiens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lichSuDongTienListModification',
            (response) => this.load(this.lichSuDongTien.id)
        );
    }
}
