import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { LichSuThaoTacHopDongService } from './lich-su-thao-tac-hop-dong.service';

@Component({
    selector: 'jhi-lich-su-thao-tac-hop-dong-detail',
    templateUrl: './lich-su-thao-tac-hop-dong-detail.component.html'
})
export class LichSuThaoTacHopDongDetailComponent implements OnInit, OnDestroy {

    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLichSuThaoTacHopDongs();
    }

    load(id) {
        this.lichSuThaoTacHopDongService.find(id)
            .subscribe((lichSuThaoTacHopDongResponse: HttpResponse<LichSuThaoTacHopDong>) => {
                this.lichSuThaoTacHopDong = lichSuThaoTacHopDongResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLichSuThaoTacHopDongs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lichSuThaoTacHopDongListModification',
            (response) => this.load(this.lichSuThaoTacHopDong.id)
        );
    }
}
