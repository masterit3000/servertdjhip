import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BatHo } from './bat-ho.model';
import { BatHoService } from './bat-ho.service';
import { LichSuDongTien } from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
@Component({
    selector: 'jhi-bat-ho-detail',
    templateUrl: './bat-ho-detail.component.html'
})
export class BatHoDetailComponent implements OnInit, OnDestroy {

    batHo: BatHo;
    lichSuDongTiens :LichSuDongTien[];
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    selected: LichSuDongTien;
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
            this.lichSuDongTien(params['id']);
            this.lichSuThaoTacHopDong(params['id']);
        });
        this.registerChangeInBatHos();
    }
    lichSuDongTien(id) {
        this.batHoService.findByHopDong(id)
            .subscribe((batHoResponse: HttpResponse<LichSuDongTien[]>) => {
                this.lichSuDongTiens = batHoResponse.body;
            });
    }
    lichSuThaoTacHopDong(id) {
        this.batHoService.findThaoTacByHopDong(id)
            .subscribe((batHoResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                this.lichSuThaoTacHopDongs = batHoResponse.body;
            });
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
