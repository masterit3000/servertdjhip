import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { VayLai } from './vay-lai.model';
import { VayLaiService } from './vay-lai.service';
import {
    LichSuDongTien,
    DONGTIEN
} from '../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { GhiNoService } from '../ghi-no/ghi-no.service';
import { GhiNo, NOTRA } from '../ghi-no';
import { Observable } from 'rxjs/Observable';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { TaiSan, TaiSanService } from '../tai-san';
@Component({
    selector: 'jhi-vay-lai-detail',
    templateUrl: './vay-lai-detail.component.html'
})
export class VayLaiDetailComponent implements OnInit, OnDestroy {
  
 
    vayLai: VayLai;
    vayLaiMoi: VayLai;
    lichSuDongTiensDaDong: LichSuDongTien[];
    lichSuDongTiensChuaDong: LichSuDongTien[];
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
    dongLai: boolean = false;
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;
    danhSachTienGocs: any[];
    lichSuGiaHans: any[];
    tonglai: number;
    traGocDialog: boolean = false;
    vayThemDialog: boolean = false;
    giaHanDialog: boolean = false;
    taiSan: TaiSan;
    taiSans: TaiSan[];

    constructor(
        private eventManager: JhiEventManager,
        private vayLaiService: VayLaiService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private jhiAlertService: JhiAlertService,
        private ghiNoService: GhiNoService,
        private router: Router,
        private route: ActivatedRoute,
        private taiSanService: TaiSanService
    ) {
        this.taiSan = new TaiSan();
        this.ghiNo = new GhiNo();
        this.lichSuDongTien = new LichSuDongTien();
        this.lichSuThaoTacHopDong = new LichSuThaoTacHopDong();
        this.vayLaiMoi = new VayLai();
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
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
    hienDongLai() {
        this.dongLai = true;
    }
    dongDongLai() {
        this.dongLai = false;
    }
    dongHopDong() {
        // this.ghiNo.trangthai = NOTRA.TRA;
        // this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
        // this.ghiNo.sotien = this.tienNo - this.tienTra;
        // if (this.ghiNo.sotien > 0) {
        //     this.setSoTienLichSuThaoTac('trả nợ', this.ghiNo.sotien, 0)
        //     this.subscribeToSaveResponse(
        //         this.ghiNoService.create(this.ghiNo));
        // }
        this.lichSuDongTienService
            .dongHopDong(this.vayLai.hopdongvl.id)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'lichSuDongTienListModification',
                    content: 'Đóng Hợp Đồng'
                });
                this.subscription = this.route.params.subscribe(params => {
                    this.load(params['id']);
                });
            });

        this.dongDongHD();
    }
    load(id) {
        this.vayLaiService
            .find(id)
            .subscribe((vayLaiResponse: HttpResponse<VayLai>) => {
                this.vayLai = vayLaiResponse.body;
                this.lichSuDongTienService
                    .findByHopDongVaTrangThai(
                        DONGTIEN.DADONG,
                        this.vayLai.hopdongvl.id
                    )
                    .subscribe(
                        (
                            lichSuDongTienResponse: HttpResponse<
                                LichSuDongTien[]
                                >
                        ) => {
                            this.lichSuDongTiensDaDong =
                                lichSuDongTienResponse.body;
                            this.tiendadong = 0;
                            for (
                                let i = 0;
                                i < lichSuDongTienResponse.body.length;
                                i++
                            ) {
                                this.tiendadong =
                                    this.tiendadong +
                                    lichSuDongTienResponse.body[i].sotien;
                            }
                        }
                    );
                this.lichSuDongTienService
                    .findByHopDongVaTrangThai(
                        DONGTIEN.CHUADONG,
                        this.vayLai.hopdongvl.id
                    )
                    .subscribe(
                        (
                            lichSuDongTienResponse: HttpResponse<
                                LichSuDongTien[]
                                >
                        ) => {
                            this.lichSuDongTiensChuaDong =
                                lichSuDongTienResponse.body;
                            this.tienchuadong = 0;
                            for (
                                let i = 0;
                                i < lichSuDongTienResponse.body.length;
                                i++
                            ) {
                                this.tienchuadong =
                                    this.tienchuadong +
                                    lichSuDongTienResponse.body[i].sotien;
                            }
                        }
                    );
                this.lichSuThaoTacHopDongService
                    .findThaoTacByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe(
                        (
                            vayLaiResponse: HttpResponse<LichSuThaoTacHopDong[]>
                        ) => {
                            this.lichSuThaoTacHopDongs = vayLaiResponse.body;
                        }
                    );
                this.ghiNoService
                    .findByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((ghiNoResponse: HttpResponse<GhiNo[]>) => {
                        this.ghiNos = ghiNoResponse.body;
                        this.tienNo = 0;
                        this.tienTra = 0;
                        for (let i = 0; i < ghiNoResponse.body.length; i++) {
                            if (
                                ghiNoResponse.body[i].trangthai.toString() ==
                                'NO'
                            ) {
                                this.tienNo =
                                    this.tienNo + ghiNoResponse.body[i].sotien;
                            } else {
                                this.tienTra =
                                    this.tienTra + ghiNoResponse.body[i].sotien;
                            }
                        }
                    }
                );
                this.taiSanService
                    .findByHopDong(this.vayLai.hopdongvl.id)
                    .subscribe((taiSanResponse: HttpResponse<TaiSan[]>) => {
                       this.taiSans = taiSanResponse.body;
                    }
                );
             
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
            response => this.load(this.vayLai.id)
        );
    }
    onRowSelect(event) {
        this.msgs = [
            {
                severity: 'info',
                summary: 'Da dong',
                detail: 'id: ' + event.data.id
            }
        ];

        this.lichSuDongTienService
            .setDongTien(event.data.id, DONGTIEN.DADONG)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'lichSuDongTienListModification',
                    content: 'Đóng Hợp Đồng'
                });
                this.subscription = this.route.params.subscribe(params => {
                    this.load(params['id']);
                });
            });
        this.setSoTienLichSuThaoTac('đóng tiền', 0, event.data.sotien);
    }
    onRowUnselect(event) {
        this.msgs = [
            {
                severity: 'info',
                summary: 'Hủy đóng',
                detail: 'id: ' + event.data.id
            }
        ];

        this.lichSuDongTienService
            .setDongTien(event.data.id, DONGTIEN.CHUADONG)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'lichSuDongTienListModification',
                    content: 'Hủy đóng Hợp Đồng'
                });
                this.subscription = this.route.params.subscribe(params => {
                    this.load(params['id']);
                });
            });
        this.setSoTienLichSuThaoTac('Hủy đóng tiền', 0, event.data.sotien);
    }
    saveNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(this.ghiNoService.update(this.ghiNo));
            this.setSoTienLichSuThaoTac('ghi nợ', this.ghiNo.sotien, 0);
        } else {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(this.ghiNoService.create(this.ghiNo));
            this.setSoTienLichSuThaoTac('ghi nợ', this.ghiNo.sotien, 0);
        }
    }
    saveTraNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(this.ghiNoService.update(this.ghiNo));
            this.setSoTienLichSuThaoTac('trả nợ', 0, this.ghiNo.sotien);
        } else {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponse(this.ghiNoService.create(this.ghiNo));
            this.setSoTienLichSuThaoTac('trả nợ', 0, this.ghiNo.sotien);
        }
    }
    saveChungTu() {
        this.isSaving = true;
        if (this.taiSan.id !== undefined) {
            this.taiSan.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponseTS(
                this.taiSanService.update(this.taiSan));
        } else {
            this.taiSan.hopDongId = this.vayLai.hopdongvl.id;
            this.subscribeToSaveResponseTS(

                this.taiSanService.create(this.taiSan));
        }
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<GhiNo>>) {
        result.subscribe(
            (res: HttpResponse<GhiNo>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }
   

    private onSaveSuccess(result: GhiNo) {
        this.eventManager.broadcast({
            name: 'ghiNoListModification',
            content: 'OK'
        });
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
                this.vayLaiService.update(this.vayLai)
            );
        } else {
            this.subscribeToSaveResponseVL(
                this.vayLaiService.create(this.vayLai)
            );
        }
    }
    traGoc(traBotGoc: string, mahopdong: string) {
        this.vayLaiMoi.cachtinhlai = this.vayLai.cachtinhlai;
        this.vayLaiMoi.thoigianvay = this.vayLai.thoigianvay;
        this.vayLaiMoi.hinhthuclai = this.vayLai.hinhthuclai;
        this.vayLaiMoi.chukylai = this.vayLai.chukylai;
        this.vayLaiMoi.lai = this.vayLai.lai;
        this.vayLaiMoi.thulaitruoc = this.vayLai.thulaitruoc;
        this.vayLaiMoi.tienvay = this.vayLai.tienvay - parseInt(traBotGoc);
        this.subscribeToSaveResponseVL(
            this.vayLaiService.themBotVayLai(
                this.vayLaiMoi,
                this.vayLai.hopdongvl.id,
                mahopdong
            )
        );
        this.setSoTienLichSuThaoTac('trả bớt gốc', 0, traBotGoc);
    }
    vayThem(vayThemGoc: string, mahopdong: string) {
        this.vayLaiMoi.cachtinhlai = this.vayLai.cachtinhlai;
        this.vayLaiMoi.thoigianvay = this.vayLai.thoigianvay;
        this.vayLaiMoi.hinhthuclai = this.vayLai.hinhthuclai;
        this.vayLaiMoi.chukylai = this.vayLai.chukylai;
        this.vayLaiMoi.lai = this.vayLai.lai;
        this.vayLaiMoi.thulaitruoc = this.vayLai.thulaitruoc;
        this.vayLaiMoi.tienvay = this.vayLai.tienvay + parseInt(vayThemGoc);
        this.subscribeToSaveResponseVL(
            this.vayLaiService.themBotVayLai(
                this.vayLaiMoi,
                this.vayLai.hopdongvl.id,
                mahopdong
            )
        );
        this.setSoTienLichSuThaoTac('vay thêm gốc', vayThemGoc, 0);
    }
    giaHan(ngayGiaHan: string) {
        this.vayLai.thoigianvay =
            this.vayLai.thoigianvay + parseInt(ngayGiaHan);
        this.subscribeToSaveResponseVL(this.vayLaiService.update(this.vayLai));
        this.setSoTienLichSuThaoTac('gia hạn vay lãi', 0, 0);
    }
    private setSoTienLichSuThaoTac(noidung: string, soTienGhiNo, soTienGhiCo) {
        this.lichSuThaoTacHopDong.hopDongId = this.vayLai.hopdongvl.id;
        this.lichSuThaoTacHopDong.soTienGhiNo = soTienGhiNo;
        this.lichSuThaoTacHopDong.soTienGhiCo = soTienGhiCo;
        this.lichSuThaoTacHopDong.noidung = noidung;
        this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong);
        this.subscribeToSaveResponseLS(
            this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong)
        );
    }
    private subscribeToSaveResponseVL(
        result: Observable<HttpResponse<VayLai>>
    ) {
        result.subscribe(
            (res: HttpResponse<VayLai>) => this.onSaveSuccessVL(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }
    private subscribeToSaveResponseTS(
        result: Observable<HttpResponse<TaiSan>>
    ) {
        result.subscribe(
            (res: HttpResponse<TaiSan>) => this.onSaveSuccessTS(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }
    private onSaveSuccessTS(result: TaiSan) {
        this.eventManager.broadcast({
            name: 'taiSanListModification',
            content: 'OK'
        });
        this.isSaving = false;
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }
    private onSaveSuccessVL(result: VayLai) {
        this.eventManager.broadcast({
            name: 'vayLaiListModification',
            content: 'OK'
        });
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
        this.router.navigate(['/vay-lai', result.id]);
    }

    private subscribeToSaveResponseLS(
        result: Observable<HttpResponse<LichSuThaoTacHopDong>>
    ) {
        result.subscribe(
            (res: HttpResponse<LichSuThaoTacHopDong>) =>
                this.onSaveSuccessLS(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccessLS(result: LichSuThaoTacHopDong) {
        this.eventManager.broadcast({
            name: 'lichSuThaoTacHopDongListModification',
            content: 'OK'
        });
        this.isSaving = false;
    }
}
