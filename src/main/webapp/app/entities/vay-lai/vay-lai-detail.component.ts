import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { VayLai } from './vay-lai.model';
import { VayLaiService } from './vay-lai.service';
import { LichSuDongTien, DONGTIEN } from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { GhiNoService } from '../ghi-no/ghi-no.service';
import { GhiNo, NOTRA } from '../ghi-no';
import { Observable } from 'rxjs/Observable';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
@Component({
    selector: 'jhi-vay-lai-detail',
    templateUrl: './vay-lai-detail.component.html'
})
export class VayLaiDetailComponent implements OnInit, OnDestroy {

    vayLai: VayLai;
    lichSuDongTiens: LichSuDongTien[];
    selected: LichSuDongTien;
    tiendadong: number;
    tienchuadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    lichSuDongTien: LichSuDongTien;
    msgs: Message[] = [];
    dongHD: boolean = false;
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;
    danhSachTienGocs: any[];
    index: number;
    constructor(
        private eventManager: JhiEventManager,
        private vayLaiService: VayLaiService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private jhiAlertService: JhiAlertService,
        private ghiNoService: GhiNoService,
        private route: ActivatedRoute
    ) {
        this.ghiNo = new GhiNo();
        this.lichSuDongTien = new LichSuDongTien();
        this.lichSuThaoTacHopDong = new LichSuThaoTacHopDong();
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
    dongDongHD() {
        this.dongHD = false;
    }
    dongHopDong() {
        this.ghiNo.trangthai = NOTRA.TRA;
        this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
        this.ghiNo.sotien = this.tienNo - this.tienTra;

        this.subscribeToSaveResponse(
            this.ghiNoService.create(this.ghiNo));
        this.lichSuDongTienService.dongHopDong(this.vayLai.hopdongvl.id)
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
    load(id) {
        this.vayLaiService.find(id)
            .subscribe((vayLaiResponse: HttpResponse<VayLai>) => {
                this.vayLai = vayLaiResponse.body;
                this.lichSuDongTienService
                    .findByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((lichSuDongTienResponse: HttpResponse<LichSuDongTien[]>) => {
                        this.lichSuDongTiens = lichSuDongTienResponse.body;
                        this.tiendadong = 0;
                        this.tienchuadong = 0;
                        for (let i = 0; i < lichSuDongTienResponse.body.length; i++) {
                            if (lichSuDongTienResponse.body[i].trangthai.toString() == "DADONG") {
                                this.tiendadong = this.tiendadong + lichSuDongTienResponse.body[i].sotien;
                            } else if (lichSuDongTienResponse.body[i].trangthai.toString() == "CHUADONG") {
                                this.tienchuadong = this.tienchuadong + lichSuDongTienResponse.body[i].sotien;
                            }
                        }
                    });
                this.lichSuThaoTacHopDongService.findThaoTacByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((vayLaiResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                        this.lichSuThaoTacHopDongs = vayLaiResponse.body;
                    });
                this.ghiNoService.findByHopDong(this.vayLai.hopdongvl.id)
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
                this.lichSuDongTien = vayLaiResponse.body;
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
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.update(this.ghiNo));
            this.setNoiDung('ghi nợ');

        } else {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.create(this.ghiNo));
            this.setNoiDung('ghi nợ');

        }

    }
    saveTraNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.update(this.ghiNo));
            this.setNoiDung('trả nợ');
        } else {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(
                this.ghiNoService.create(this.ghiNo));
            this.setNoiDung('trả nợ');  
        }

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
        if (this.vayLai.id !== undefined) {
            this.subscribeToSaveResponseVL(
                this.vayLaiService.update(this.vayLai));
        } else {
            this.subscribeToSaveResponseVL(
                this.vayLaiService.create(this.vayLai));
        }
    }
    traGoc(traBotGoc: string) {
        this.vayLai.tienvay = this.vayLai.tienvay - parseInt(traBotGoc);
        this.subscribeToSaveResponseVL(
            this.vayLaiService.update(this.vayLai));
            this.setNoiDung('trả bớt gốc');  
    }
    vayThem(vayThemGoc: string) {
        this.vayLai.tienvay = this.vayLai.tienvay + parseInt(vayThemGoc);
        this.subscribeToSaveResponseVL(
            this.vayLaiService.update(this.vayLai));
            this.setNoiDung('vay thêm gốc');  
    }
    giaHan(ngayGiaHan: string) {
        this.vayLai.thoigianvay = this.vayLai.thoigianvay + parseInt(ngayGiaHan);
        this.subscribeToSaveResponseVL(
            this.vayLaiService.update(this.vayLai));
            this.setNoiDung('gia hạn vay lãi');  
    }
    private subscribeToSaveResponseVL(result: Observable<HttpResponse<VayLai>>) {
        result.subscribe((res: HttpResponse<VayLai>) =>
            this.onSaveSuccessVL(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }
    private onSaveSuccessVL(result: VayLai) {
        this.eventManager.broadcast({ name: 'vayLaiListModification', content: 'OK' });
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
        this.previousState();
    }
    private setNoiDung(noidung: string) {
        this.lichSuThaoTacHopDong.hopDongId = this.vayLai.hopdongvl.id;
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
        this.eventManager.broadcast({ name: 'lichSuThaoTacHopDongListModification', content: 'OK'});
        this.isSaving = false;
    }
}
