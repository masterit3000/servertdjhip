import { Component, OnInit, OnDestroy } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { BatHo } from './bat-ho.model';
import { BatHoService } from './bat-ho.service';
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
    selector: 'jhi-bat-ho-detail',
    templateUrl: './bat-ho-detail.component.html'
})
export class BatHoDetailComponent implements OnInit, OnDestroy {
    batHo: BatHo;
    batHos: BatHo[];
    batHoDao: BatHo;
    lichSuDongTiensDaDong: LichSuDongTien[];
    lichSuDongTiensChuaDong: LichSuDongTien[];
    lichSuDongTien: LichSuDongTien;
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    selected: LichSuDongTien[];
    msgs: Message[] = [];
    tiendadong: number;
    tienchuadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    dongHD: boolean = false;
    dongGhiNo: boolean = false;
    dongTraNo: boolean = false;
    showDaoHo: boolean = false;
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;
    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    soTienGhiNo: number;
    soTienGhiCo: number;
    images: any[];
    taiSan: TaiSan;
    taiSans: TaiSan[];

    constructor(
        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private ghiNoService: GhiNoService,
        private jhiAlertService: JhiAlertService,
        private router: Router,
        private route: ActivatedRoute,
        private taiSanService: TaiSanService
    ) // private confirmationService: ConfirmationService
    {
        this.taiSan = new TaiSan();
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
        this.images = [];
    }

    hienDongHD() {
        this.dongHD = true;
    }

    dongDongHD() {
        this.dongHD = false;
    }

    dongHopDong() {
        // this.ghiNo.trangthai = NOTRA.TRA;
        // this.ghiNo.hopDongId = this.batHo.hopdong.id;
        // this.ghiNo.sotien = this.tienNo - this.tienTra;
        // if (this.ghiNo.sotien > 0) {
        //     this.setSoTienLichSuThaoTac('trả nợ', 0, this.ghiNo.sotien);

        //     this.subscribeToSaveResponse(
        //         this.ghiNoService.create(this.ghiNo));
        // }
        this.lichSuDongTienService
            .dongHopDong(this.batHo.hopdong.id)
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

    // convertotEnum
    load(id) {
        this.batHoService
            .find(id)
            .subscribe((batHoResponse: HttpResponse<BatHo>) => {
                this.batHo = batHoResponse.body;
                this.lichSuDongTienService
                    .findByHopDongVaTrangThai(
                        DONGTIEN.DADONG,
                        this.batHo.hopdong.id
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
                        this.batHo.hopdong.id
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
                    .findThaoTacByHopDong(this.batHo.hopdong.id)
                    .subscribe(
                        (
                            batHoResponse: HttpResponse<LichSuThaoTacHopDong[]>
                        ) => {
                            this.lichSuThaoTacHopDongs = batHoResponse.body;
                        }
                    );
                this.ghiNoService
                    .findByHopDong(this.batHo.hopdong.id)
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
                    });
                    this.taiSanService
                    .findByHopDong(this.batHo.hopdong.id)
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

    registerChangeInBatHos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'batHoListModification',
            response => this.load(this.batHo.id)
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
        this.setSoTienLichSuThaoTac('Hủy đóng tiền', event.data.sotien, 0);
    }

    saveNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(this.ghiNoService.update(this.ghiNo));
        } else {
            this.ghiNo.trangthai = NOTRA.NO;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(this.ghiNoService.create(this.ghiNo));
        }
        this.setSoTienLichSuThaoTac('Ghi nợ', this.ghiNo.sotien, 0);
        this.dongGhiNo = true;
    }
    saveTraNo() {
        this.isSaving = true;
        if (this.ghiNo.id !== undefined) {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(this.ghiNoService.update(this.ghiNo));
        } else {
            this.ghiNo.trangthai = NOTRA.TRA;
            this.ghiNo.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponse(this.ghiNoService.create(this.ghiNo));
        }
        this.setSoTienLichSuThaoTac('Trả nợ', 0, this.ghiNo.sotien);
        this.dongTraNo = true;
    }
    saveChungTu(){
        this.isSaving = true;
        if (this.taiSan.id !== undefined) {
            this.taiSan.hopDongId = this.batHo.hopdong.id;
            this.subscribeToSaveResponseTS(
                this.taiSanService.update(this.taiSan));
        } else {
            this.taiSan.hopDongId = this.batHo.hopdong.id;
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
        if (this.batHo.id !== undefined) {
            this.subscribeToSaveResponseBH(
                this.batHoService.update(this.batHo)
            );
        } else {
            this.dongHopDong();
            this.subscribeToSaveResponseBH(
                this.batHoService.create(this.batHo)
            );
        }
    }
    private subscribeToSaveResponseBH(result: Observable<HttpResponse<BatHo>>) {
        result.subscribe(
            (res: HttpResponse<BatHo>) => this.onSaveSuccessBH(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }
    private onSaveSuccessBH(result: BatHo) {
        this.eventManager.broadcast({
            name: 'batHoListModification',
            content: 'OK'
        });
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.router.navigate(['/bat-ho', result.id]);
        this.jhiAlertService.success('Thêm mới thành công', null, null);
    }
    daoHo(mahopdong: string) {
        this.dongHopDong();
        this.subscribeToSaveResponseBH(
            this.batHoService.daoHo(
                this.batHoDao,
                this.batHo.hopdong.id,
                mahopdong
            )
        );
        this.showDaoHo = true;
    }
    private setSoTienLichSuThaoTac(noidung: string, soTienGhiNo, soTienGhiCo) {
        this.lichSuThaoTacHopDong.hopDongId = this.batHo.hopdong.id;
        this.lichSuThaoTacHopDong.soTienGhiNo = soTienGhiNo;
        this.lichSuThaoTacHopDong.soTienGhiCo = soTienGhiCo;
        this.lichSuThaoTacHopDong.noidung = noidung;
        this.subscribeToSaveResponseLS(
            this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong)
        );
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
    // findHopDong() {
    //     this.batHoService.findByHopDong(this.batHo.hopdong.id)
    //         .subscribe((batHoResponse: HttpResponse<BatHo[]>) => {
    //             this.batHos = batHoResponse.body;
    //         });
    // }
}
