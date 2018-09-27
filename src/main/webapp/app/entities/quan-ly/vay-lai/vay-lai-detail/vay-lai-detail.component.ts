import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VayLai } from '../../../vay-lai/vay-lai.model';
import { VayLaiService } from '../../../vay-lai/vay-lai.service';
import {
    LichSuDongTien,
    DONGTIEN
} from '../../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuThaoTacHopDong } from '../../../lich-su-thao-tac-hop-dong';
import { LichSuDongTienService } from '../../../lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuThaoTacHopDongService } from '../../../lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { TaiSan, TaiSanService } from '../../../tai-san';
import { GhiNo, GhiNoService } from '../../../ghi-no';
import { Observable } from '../../../../../../../../node_modules/rxjs';
import { Message } from '../../../../../../../../node_modules/primeng/primeng';

@Component({
    selector: 'vay-lai-detail-admin',
    templateUrl: './vay-lai-detail.component.html'
})
export class VayLaiDetailAdminComponent implements OnInit, OnDestroy {
    vayLai: VayLai;
    lichSuDongTiensDaDong: LichSuDongTien[];
    lichSuDongTiensChuaDong: LichSuDongTien[];
    selected: LichSuDongTien;
    tiendadong: number;
    tienchuadong: number;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    lichSuThaoTacHopDongs: LichSuThaoTacHopDong[];
    dongHD: boolean = false;
    taiSan: TaiSan;
    taiSans: TaiSan[];
    ghiNo: GhiNo;
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    isSaving: boolean;
    lichSuThaoTacHopDong: LichSuThaoTacHopDong;
    msgs: Message[] = [];
    constructor(
        private eventManager: JhiEventManager,
        private vayLaiService: VayLaiService,
        private taiSanService: TaiSanService,
        private ghiNoService: GhiNoService,
        private lichSuDongTienService: LichSuDongTienService,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        private route: ActivatedRoute
    ) { }

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
}
