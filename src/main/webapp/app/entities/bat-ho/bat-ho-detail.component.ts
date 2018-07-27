import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { BatHo } from './bat-ho.model';
import { BatHoService } from './bat-ho.service';
import { LichSuDongTien, DONGTIEN } from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
@Component({
    selector: 'jhi-bat-ho-detail',
    templateUrl: './bat-ho-detail.component.html'
})
export class BatHoDetailComponent implements OnInit, OnDestroy {
    batHo: BatHo;
    lichSuDongTiens: LichSuDongTien[];
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    selected: LichSuDongTien;
    msgs: Message[] = [];
    tiendadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private route: ActivatedRoute,
        // private confirmationService: ConfirmationService
    ) { }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.lichSuThaoTacHopDong(params['id']);

        });
        this.registerChangeInBatHos();
    }

    lichSuThaoTacHopDong(id) {

    }

    load(id) {
        this.batHoService
            .find(id)
            .subscribe((batHoResponse: HttpResponse<BatHo>) => {
                this.batHo = batHoResponse.body;
                this.lichSuDongTienService
                    .findByHopDong(this.batHo.hopdong.id)
                    .subscribe((lichSuDongTienResponse: HttpResponse<LichSuDongTien[]>) => {
                        this.lichSuDongTiens = lichSuDongTienResponse.body;
                        this.tiendadong = 0;
                        for (let i = 0; i < lichSuDongTienResponse.body.length; i++) {
                            if (lichSuDongTienResponse.body[i].trangthai === DONGTIEN['DADONG']) {
                                this.tiendadong = this.tiendadong + lichSuDongTienResponse.body[i].sotien;
                            }else{
                                this.tiendadong = 100;
                            }
                        }
                    });
                this.lichSuThaoTacHopDongService.findThaoTacByHopDong(id)
                    .subscribe((batHoResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                        this.lichSuThaoTacHopDongs = batHoResponse.body;
                    });
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
            response => this.load(this.batHo.id)
        );
    }
    onRowSelect(event) {
        this.msgs = [{ severity: 'info', summary: 'Da dong', detail: 'id: ' + event.data.id }];
        this.batHoService.setDongTien(event.data.id)
            .subscribe((batHoResponse: HttpResponse<LichSuDongTien>) => {
                this.batHo = batHoResponse.body;
            });
    }

    onRowUnselect(event) {
        this.msgs = [{ severity: 'info', summary: 'Car Selected', detail: 'Vin: ' + event.id }];
    }

}
