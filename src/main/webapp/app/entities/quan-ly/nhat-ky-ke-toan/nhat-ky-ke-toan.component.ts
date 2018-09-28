import { Component, OnInit, OnDestroy } from "@angular/core";
import { HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Subscription } from "rxjs/Subscription";
import { JhiEventManager, JhiAlertService } from "ng-jhipster";

import { NhatKy } from "../../nhat-ky/nhat-ky.model";
import { NhatKyService } from "../../nhat-ky/nhat-ky.service";
import { Principal } from "../../../shared";
@Component({
    selector: "jhi-nhat-ky-ke-toan",
    templateUrl: "./nhat-ky-ke-toan.component.html",
    styles: []
})
export class NhatKyKeToanComponent implements OnInit {
    nhatKies: NhatKy[];
    currentAccount: any;
    eventSubscriber: Subscription;
    keyTimNhatKy: any;
    constructor(
        private nhatKyService: NhatKyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}
    loadAll() {
        this.nhatKyService.query().subscribe(
            (res: HttpResponse<NhatKy[]>) => {
                this.nhatKies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNhatKies();
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NhatKy) {
        return item.id;
    }
    registerChangeInNhatKies() {
        this.eventSubscriber = this.eventManager.subscribe(
            "nhatKyListModification",
            response => this.loadAll()
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    timNhatKy() {
        this.nhatKyService.findNhatKy(this.keyTimNhatKy).subscribe(
            (res: HttpResponse<NhatKy[]>) => {
                this.nhatKies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}