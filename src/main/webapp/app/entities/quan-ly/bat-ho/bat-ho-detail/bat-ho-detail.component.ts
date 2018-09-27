import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { BatHo } from '../../../bat-ho/bat-ho.model';
import { BatHoService } from '../../../bat-ho/bat-ho.service';
import { LichSuDongTien, DONGTIEN } from '../../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../../../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../../../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../../../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { TaiSanService, TaiSan } from '../../../tai-san';
import { GhiNo, GhiNoService } from '../../../ghi-no';
import { Observable } from '../../../../../../../../node_modules/rxjs';
@Component({
    selector: 'bat-ho-detail-admin',
    templateUrl: './bat-ho-detail.component.html'
})
export class BatHoDetailAdminComponent implements OnInit, OnDestroy {
    batHo: BatHo;
    lichSuDongTiensDaDong: LichSuDongTien[];
    lichSuDongTiensChuaDong: LichSuDongTien[];
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    selected: LichSuDongTien;
    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    msgs: Message[] = [];
    tiendadong: number;
    tienchuadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    dongHD: boolean = false;
    taiSan: TaiSan;
    taiSans: TaiSan[];
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;


    constructor(
        private eventManager: JhiEventManager,
        private batHoService: BatHoService,
        private taiSanService: TaiSanService,
        private ghiNoService: GhiNoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private route: ActivatedRoute,
        

        // private confirmationService: ConfirmationService
    ) {
        this.lichSuThaoTacHopDong = new LichSuThaoTacHopDong();
    }

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
                this.lichSuThaoTacHopDongService.findThaoTacByHopDong(this.batHo.hopdong.id)
                    .subscribe((batHoResponse: HttpResponse<LichSuThaoTacHopDong[]>) => {
                        this.lichSuThaoTacHopDongs = batHoResponse.body;
                    });
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
    private setSoTienLichSuThaoTac(noidung: string, soTienGhiNo, soTienGhiCo) {
        this.lichSuThaoTacHopDong.hopDongId = this.batHo.hopdong.id;
        this.lichSuThaoTacHopDong.soTienGhiNo = soTienGhiNo;
        this.lichSuThaoTacHopDong.soTienGhiCo = soTienGhiCo;
        this.lichSuThaoTacHopDong.noidung = noidung;
        this.subscribeToSaveResponseLS(
            this.lichSuThaoTacHopDongService.create(this.lichSuThaoTacHopDong)
        );
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
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
    private onSaveError() {
        this.isSaving = false;
    }
    registerChangeInBatHos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'batHoListModification',
            response => this.load(this.batHo.id)
        );
    }


}
