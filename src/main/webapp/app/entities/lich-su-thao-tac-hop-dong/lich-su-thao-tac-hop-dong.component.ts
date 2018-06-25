import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { LichSuThaoTacHopDongService } from './lich-su-thao-tac-hop-dong.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-lich-su-thao-tac-hop-dong',
    templateUrl: './lich-su-thao-tac-hop-dong.component.html'
})
export class LichSuThaoTacHopDongComponent implements OnInit, OnDestroy {
lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.lichSuThaoTacHopDongService.query().subscribe(
            (res: HttpResponse<LichSuThaoTacHopDong[]>) => {
                this.lichSuThaoTacHopDongs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLichSuThaoTacHopDongs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LichSuThaoTacHopDong) {
        return item.id;
    }
    registerChangeInLichSuThaoTacHopDongs() {
        this.eventSubscriber = this.eventManager.subscribe('lichSuThaoTacHopDongListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
