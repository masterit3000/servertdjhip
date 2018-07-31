import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { VayLai } from './vay-lai.model';
import { VayLaiService } from './vay-lai.service';
import { LichSuDongTien, DONGTIEN } from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
@Component({
    selector: 'jhi-vay-lai-detail',
    templateUrl: './vay-lai-detail.component.html'
})
export class VayLaiDetailComponent implements OnInit, OnDestroy {

    vayLai: VayLai;
    lichSuDongTiens: LichSuDongTien[];
    selected: LichSuDongTien;
    tiendadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    msgs: Message[] = [];
    dongHD: boolean = false;
    constructor(
        private eventManager: JhiEventManager,
        private vayLaiService: VayLaiService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVayLais();
    }
    hienDongHD() {
        this.dongHD = true;
    }
    dongDongHD(){
        this.dongHD = false;
    }
    load(id) {
        this.vayLaiService.find(id)
            .subscribe((vayLaiResponse: HttpResponse<VayLai>) => {
                this.vayLai = vayLaiResponse.body;
                this.lichSuDongTienService
                    .findByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((lichSuDongTienResponse: HttpResponse<LichSuDongTien[]>) => {
                        this.lichSuDongTiens = lichSuDongTienResponse.body;
                        this.tiendadong = 0;
                        for (let i = 0; i < lichSuDongTienResponse.body.length; i++) {
                            if (lichSuDongTienResponse.body[i].trangthai.toString() == "DADONG") {
                                this.tiendadong = this.tiendadong + lichSuDongTienResponse.body[i].sotien;
                            }
                        }
                    });
                this.lichSuThaoTacHopDongService.findThaoTacByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((vayLaiResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                        this.lichSuThaoTacHopDongs = vayLaiResponse.body;
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

    registerChangeInVayLais() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vayLaiListModification',
            (response) => this.load(this.vayLai.id)
        );
    }
    onRowSelect(event) {
        this.msgs = [{ severity: 'info', summary: 'Da dong', detail: 'id: ' + event.data.id }];
        this.lichSuDongTienService.setDongTien(event.data.id)
            .subscribe((vayLaiResponse: HttpResponse<LichSuDongTien>) => {
                this.vayLai = vayLaiResponse.body;
            });
    }
}
