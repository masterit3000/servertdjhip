import { Component, OnInit, OnDestroy } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
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
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';


@Component({
    selector: 'jhi-bat-ho-detail',
    templateUrl: './bat-ho-detail.component.html',

})
export class BatHoDetailComponent implements OnInit, OnDestroy {
    batHo: BatHo;
    batHos: BatHo[];
    batHoDao: BatHo;
    lichSuDongTiens: LichSuDongTien[];
    lichSuDongTien: LichSuDongTien;
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    selected: LichSuDongTien[];
    msgs: Message[] = [];
    tiendadong: number;
    tienchuadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    dongHD: boolean = false;
    dongTien: boolean = false;
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;
    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    


    constructor(

        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private ghiNoService: GhiNoService,
        private jhiAlertService: JhiAlertService,
        private route: ActivatedRoute,
        // private confirmationService: ConfirmationService
    ) {
        this.ghiNo = new GhiNo();
        this.batHoDao = new BatHo();
        this.lichSuDongTien = new LichSuDongTien();
        this.selected = new Array<LichSuDongTien>();
        this.lichSuThaoTacHopDong = new LichSuThaoTacHopDong();
    }
    ngAfterViewInit() {
        // let j = 0;
        // if (this.lichSuDongTiens == null) this.ngAfterViewInit();

    }
    // ngOnChanges() {


    // }
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

    hienDongTien(){
        this.dongTien = true;
    }

    dongDongTien(){
        this.dongTien = false;
    }

    dongHopDong() {
        this.ghiNo.trangthai = NOTRA.TRA;
        this.ghiNo.hopDongId = this.batHo.hopdong.id;
        this.ghiNo.sotien = this.tienNo - this.tienTra;

        this.subscribeToSaveResponse(
            this.ghiNoService.create(this.ghiNo));
        this.lichSuDongTienService.dongHopDong(this.batHo.hopdong.id)
            .subscribe((response) => {
                this.eventManager.broadcast({
                    name: 'lichSuDongTienListModification',
                    content: 'Đóng Hợp Đồng'
                });


                this.subscription = this.route.params.subscribe(params => {
                    this.load(params['id']);

                });
            });
        this.setNoiDung('đóng hợp đồng');
        this.dongDongHD();

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
                        this.tienchuadong = 0;
                        for (let i = 0; i < lichSuDongTienResponse.body.length; i++) {
                            if (lichSuDongTienResponse.body[i].trangthai.toString() == "DADONG") {
                                this.tiendadong = this.tiendadong + lichSuDongTienResponse.body[i].sotien;
                                // this.selected[j++] = this.lichSuDongTiens[i];

                            } else if (lichSuDongTienResponse.body[i].trangthai.toString() == "CHUADONG") {
                                this.tienchuadong = this.tienchuadong + lichSuDongTienResponse.body[i].sotien;
                            }

                        }
                        let j = 0;
                        for (let i = 0; i < this.lichSuDongTiens.length; i++) {
                            if (this.lichSuDongTiens[i].trangthai.toString() == 'DADONG') {
                                this.selected[j++] = this.lichSuDongTiens[i];
                                console.log(this.lichSuDongTiens[i]);

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
                this.lichSuDongTien = batHoResponse.body;
                this.subscription = this.route.params.subscribe(params => {
                    this.load(params['id']);

                });
            });
        this.setNoiDung('đóng tiền');

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
        this.setNoiDung('ghi nợ');
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
        this.setNoiDung('trả nợ');
    }


    private subscribeToSaveResponse(result: Observable<HttpResponse<GhiNo>>) {
        result.subscribe((res: HttpResponse<GhiNo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GhiNo) {
        this.eventManager.broadcast({ name: 'ghiNoListModification', content: 'OK' });
        this.isSaving = false;
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });
    }

    private onSaveError() {
        this.isSaving = false;
    }
    save() {
        this.isSaving = true;
        if (this.batHo.id !== undefined) {
            this.subscribeToSaveResponseBH(
                this.batHoService.update(this.batHo));
        } else {
            this.dongHopDong();
            this.subscribeToSaveResponseBH(
                this.batHoService.create(this.batHo));
        }
    }
    private subscribeToSaveResponseBH(result: Observable<HttpResponse<BatHo>>) {
        result.subscribe((res: HttpResponse<BatHo>) =>
            this.onSaveSuccessBH(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }
    private onSaveSuccessBH(result: BatHo) {
        this.eventManager.broadcast({ name: 'batHoListModification', content: 'OK' });
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
        this.previousState();
    }
    daoHo() {
        this.dongHopDong();
        this.subscribeToSaveResponseBH(
            this.batHoService.daoHo(this.batHoDao, this.batHo.hopdong.id));
        this.setNoiDung('đảo họ');
    }
    private setNoiDung(noidung: string) {
        this.lichSuThaoTacHopDong.hopDongId = this.batHo.hopdong.id;
        this.lichSuThaoTacHopDong.noidung = noidung;
        this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong)
        this.subscribeToSaveResponseLS(
            this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong));
    }
    private subscribeToSaveResponseLS(result: Observable<HttpResponse<LichSuThaoTacHopDong>>) {
        result.subscribe((res: HttpResponse<LichSuThaoTacHopDong>) =>
            this.onSaveSuccessLS(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccessLS(result: LichSuThaoTacHopDong) {
        this.eventManager.broadcast({ name: 'lichSuThaoTacHopDongListModification', content: 'OK' });
        this.isSaving = false;
    }
    findHopDong(){
        this.batHoService.findByHopDong(this.batHo.hopdong.id)
        .subscribe((batHoResponse: HttpResponse<BatHo[]>) => {
            this.batHos = batHoResponse.body;
        });
    }
   

}
