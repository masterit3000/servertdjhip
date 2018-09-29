import { Component, OnInit, OnDestroy } from "@angular/core";
import { HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Subscription } from "rxjs/Subscription";
import { JhiEventManager, JhiAlertService } from "ng-jhipster";

import { NhatKy } from "../../nhat-ky/nhat-ky.model";
import { NhatKyService } from "../../nhat-ky/nhat-ky.service";
import { Principal } from "../../../shared";
import { CuaHangService, CuaHang } from "../../cua-hang";
@Component({
    selector: "jhi-nhat-ky-ke-toan",
    templateUrl: "./nhat-ky-ke-toan.component.html",
    styles: []
})
export class NhatKyKeToanComponent implements OnInit {
    nhatKies: NhatKy[];
    cuaHangs: CuaHang[];
    cuaHang: CuaHang;
    currentAccount: any;
    eventSubscriber: Subscription;
    keyTimNhatKy: any;
    selectedCuaHang: CuaHang;
    constructor(
        private nhatKyService: NhatKyService,
        private cuaHangService: CuaHangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}
    chonCuaHang() {
        this.nhatKyService.getAllByCuaHAng(this.selectedCuaHang.id).subscribe(
            (res: HttpResponse<NhatKy[]>) => {
                this.nhatKies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadCuaHang();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });

    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NhatKy) {
        return item.id;
    }


    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    timNhatKy() {
        this.nhatKyService.findNhatKyByCuaHang(this.keyTimNhatKy,this.selectedCuaHang.id).subscribe(
            (res: HttpResponse<NhatKy[]>) => {
                this.nhatKies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadCuaHang() {
        this.cuaHangService.query().subscribe(
            (res: HttpResponse<CuaHang[]>) => {
                this.cuaHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}
