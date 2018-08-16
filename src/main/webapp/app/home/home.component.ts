
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Account, LoginModalService } from '../shared';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuDongTien, LichSuDongTienService, DONGTIEN } from '../../app/entities/lich-su-dong-tien';
import { Principal } from '../shared';
import { HopDong, HopDongService, LOAIHOPDONG, TRANGTHAIHOPDONG } from '../../app/entities/hop-dong';
import { GhiNo, GhiNoService } from '../../app/entities/ghi-no';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    lichSuDongTiens: LichSuDongTien[];
    modalRef: NgbModalRef;
    currentAccount: any;
    eventSubscriber: Subscription;
    lichSuDongTienBHs: LichSuDongTien[];
    lichSuDongTienHomNayBHs: LichSuDongTien[];
    lichSuDongTienVLs: LichSuDongTien[];
    lichSuDongTienHomNayVLs: LichSuDongTien[];
    hopDongs: HopDong[];
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    selected:any;
    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private lichSuDongTienService: LichSuDongTienService,
        private hopDongService: HopDongService,
        private ghiNoService: GhiNoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.loadLichSuTraChamBatHo();
        this.loadLichSuTraChamVayLai();
        this.loadLichSuTraBatHoHomNay();
        this.loadLichSuTraVayLaiHomNay();
        this.loadHopDong();

        this.registerAuthenticationSuccess();
    }


    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    loadLichSuTraChamBatHo() {
        this.lichSuDongTienService.lichSuTraCham(DONGTIEN.CHUADONG, LOAIHOPDONG.BATHO).subscribe(
            (res: HttpResponse<LichSuDongTien[]>) => {
                this.lichSuDongTienBHs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadLichSuTraChamVayLai() {
        this.lichSuDongTienService.lichSuTraCham(DONGTIEN.CHUADONG, LOAIHOPDONG.VAYLAI).subscribe(
            (res: HttpResponse<LichSuDongTien[]>) => {
                this.lichSuDongTienVLs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadLichSuTraBatHoHomNay() {
        this.lichSuDongTienService.lichSuTraHomNay(DONGTIEN.CHUADONG, LOAIHOPDONG.BATHO).subscribe(
            (res: HttpResponse<LichSuDongTien[]>) => {
                this.lichSuDongTienHomNayBHs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadLichSuTraVayLaiHomNay() {
        this.lichSuDongTienService.lichSuTraHomNay(DONGTIEN.CHUADONG, LOAIHOPDONG.VAYLAI).subscribe(
            (res: HttpResponse<LichSuDongTien[]>) => {
                this.lichSuDongTienHomNayVLs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadHopDong() {
        this.hopDongService.thongkehopdong(TRANGTHAIHOPDONG.DADONG).subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

