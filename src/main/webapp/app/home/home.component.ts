
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
import { BatHo, BatHoService } from '../../app/entities/bat-ho';
import { VayLai, VayLaiService } from '../../app/entities/vay-lai';
import { Router } from "@angular/router";
import { Angular5Csv } from 'angular5-csv/Angular5-csv';
@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    batHo: BatHo;
    batHosTraCham: BatHo[];
    vayLaisTraCham: VayLai[];
    vayLai: VayLai;
    lichSuDongTiens: LichSuDongTien[];
    modalRef: NgbModalRef;
    currentAccount: any;
    eventSubscriber: Subscription;
    lichSuDongTienBHs: LichSuDongTien[];
    lichSuDongTienHomNayBHs: LichSuDongTien[];
    lichSuDongTienVLs: LichSuDongTien[];
    lichSuDongTienHomNayVLs: LichSuDongTien[];
    hopDongBHs: HopDong[];
    hopDongDangNoBHs: HopDong[];
    hopDongVLs: HopDong[];
    hopDongDangNoVLs: HopDong[];
    ghiNos: GhiNo[];
    tienNo: number;
    tienTra: number;
    selected: any;
    cols: any[];
    songaytracham:number;
    sotientracham:number;
    temp:number;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private lichSuDongTienService: LichSuDongTienService,
        private hopDongService: HopDongService,
        private ghiNoService: GhiNoService,
        private router: Router,
        private batHoService: BatHoService,
        private vayLaiService: VayLaiService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.cols = [
            { field: 'mahopdong', header: 'Ma hop dong' },
            { field: 'ngaytao', header: 'Ngay tao ' },
            { field: 'nhanVienTen', header: 'Nhan Vien ' },
            { field: 'khachHangTen', header: 'Khach hang' }
        ];

    }

    ngOnInit() {

        this.loadLichSuTraBatHoHomNay();
        this.loadLichSuTraVayLaiHomNay();
        this.loadHopDongBH();
        this.loadHopDongVL();
        this.loadHopDongDangNoVL();
        this.loadHopDongDangNoBH();
        this.principal.identity().then((account) => {
            this.account = account;
        });
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
    // saveexcel() {
    //     var options = {
    //         fieldSeparator: ',',
    //         quoteStrings: '"',
    //         decimalseparator: '.',
    //         showLabels: true,
    //         showTitle: true,
    //         useBom: true,
    //         noDownload: false,
    //         headers: ["Ma hop dong", "Ngay tao", "Nhan vien","Khach hang"]
    //       };

    //       Angular5Csv(this.hopDongDangNoBHs, "filename.csv", options);

    // }

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
    loadHopDongBH() {
        this.hopDongService.getHopDongsTraCham(LOAIHOPDONG.BATHO).subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongBHs = res.body;

                
                this.hopDongBHs.forEach(hopdong => {
                    this.lichSuDongTienService.findByHopDongVaTrangThai(DONGTIEN.CHUADONG,hopdong.id).subscribe(
                        (res: HttpResponse<LichSuDongTien[]>) => {
                            this.lichSuDongTienBHs = res.body;
                            this.songaytracham =0;
                            this.sotientracham =0;
                            this.lichSuDongTienBHs.forEach(element => {
                                this.songaytracham++;
                                this.sotientracham+=element.sotien;
                            });
                            hopdong.songaytracham = this.songaytracham;
                            hopdong.sotientracham = this.sotientracham;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)

                    );

                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadHopDongDangNoBH() {
        this.hopDongService.thongkehopdong(TRANGTHAIHOPDONG.DANGVAY, LOAIHOPDONG.BATHO).subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongDangNoBHs = res.body;
            
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadHopDongVL() {
        this.hopDongService.getHopDongsTraCham(LOAIHOPDONG.VAYLAI).subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongVLs = res.body;

                
                this.hopDongVLs.forEach(hopdong => {
                    this.lichSuDongTienService.findByHopDongVaTrangThai(DONGTIEN.CHUADONG,hopdong.id).subscribe(
                        (res: HttpResponse<LichSuDongTien[]>) => {
                            this.lichSuDongTienVLs = res.body;
                            this.songaytracham =0;
                            this.sotientracham =0;
                            this.lichSuDongTienVLs.forEach(element => {
                                this.songaytracham++;
                                this.sotientracham+=element.sotien;
                            });
                            hopdong.songaytracham = this.songaytracham;
                            hopdong.sotientracham = this.sotientracham;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)

                    );

                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadHopDongDangNoVL() {
        this.hopDongService.thongkehopdong(TRANGTHAIHOPDONG.DANGVAY, LOAIHOPDONG.VAYLAI).subscribe(
            (res: HttpResponse<HopDong[]>) => {
                this.hopDongDangNoVLs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    findBatHoByHopDong(id: number) {
        this.batHoService.findByHopDong(id).subscribe(
            (res: HttpResponse<BatHo>) => {
                this.batHo = res.body;
                this.router.navigate(['/bat-ho', this.batHo.id]);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }
    findVayLaiByHopDong(id: number) {
        this.vayLaiService.findByHopDong(id).subscribe(
            (res: HttpResponse<VayLai>) => {
                this.vayLai = res.body;
                this.router.navigate(['/vay-lai', this.vayLai.id]);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

}

