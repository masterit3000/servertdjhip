import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { BatHo } from './bat-ho.model';
import { BatHoService } from './bat-ho.service';
import { LichSuDongTien, DONGTIEN } from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { GhiNoService } from '../ghi-no/ghi-no.service';
import { GhiNo, NOTRA } from '../ghi-no';
import { Observable } from 'rxjs/Observable';

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
    dongHD: boolean = false;
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;


    constructor(

        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private ghiNoService: GhiNoService,
        private route: ActivatedRoute,
        // private confirmationService: ConfirmationService
    ) { this.ghiNo = new GhiNo(); }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });
        this.registerChangeInBatHos();
    }

    hienDongHD() {
        this.dongHD = true;
    }
    dongDongHD() {
        this.dongHD = false;
    }

    // convertotEnum
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
                            if (lichSuDongTienResponse.body[i].trangthai.toString() == "DADONG") {
                                this.tiendadong = this.tiendadong + lichSuDongTienResponse.body[i].sotien;
                            }
                        }
                    });
                this.lichSuThaoTacHopDongService.findThaoTacByHopDong(this.batHo.hopdong.id)
                    .subscribe((batHoResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                        this.lichSuThaoTacHopDongs = batHoResponse.body;
                    });
                this.ghiNoService.findByHopDong(this.batHo.hopdong.id)
                    .subscribe((ghiNoResponse: HttpResponse<GhiNo[]>) => {
                        this.ghiNos = ghiNoResponse.body;
                        this.tienNo = 0;
                        this.tienTra = 0;
                        for (let i = 0; i < ghiNoResponse.body.length; i++) {
                            if (ghiNoResponse.body[i].trangthai.toString() == "NO") {
                                this.tienNo = this.tienNo + ghiNoResponse.body[i].sotien;
                            } else {
                                this.tienTra = this.tienTra + ghiNoResponse.body[i].sotien;
                            }
                        }

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
        this.lichSuDongTienService.setDongTien(event.data.id)
            .subscribe((batHoResponse: HttpResponse<LichSuDongTien>) => {
                this.batHo = batHoResponse.body;
            });
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });
    }

    saveNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.update(this.ghiNo));
        } else {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.create(this.ghiNo));
        }
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });

    }
    saveTraNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.update(this.ghiNo));
        } else {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.create(this.ghiNo));
        }
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });
    }


    private subscribeToSaveResponse(result: Observable<HttpResponse<GhiNo>>) {
        result.subscribe((res: HttpResponse<GhiNo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GhiNo) {
        this.eventManager.broadcast({ name: 'ghiNoListModification', content: 'OK' });
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }


}
