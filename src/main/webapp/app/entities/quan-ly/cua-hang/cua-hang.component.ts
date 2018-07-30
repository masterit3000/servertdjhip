import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CuaHang } from '../../cua-hang/cua-hang.model';
import { CuaHangService } from '../../cua-hang/cua-hang.service';
import { Principal } from '../../../shared';

@Component({
    selector: 'cua-hang-admin',
    templateUrl: './cua-hang.component.html'
})
export class CuaHangAdminComponent implements OnInit, OnDestroy {
    cuaHangs: CuaHang[];
    currentAccount: any;
    eventSubscriber: Subscription;
    filteredCuaHangs: CuaHang[];
    none: any;
    cuaHang: CuaHang;
    cuahangs: CuaHang[];

    constructor(
        private cuaHangService: CuaHangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cuaHangService.query().subscribe(
            (res: HttpResponse<CuaHang[]>) => {
                this.cuaHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCuaHangs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CuaHang) {
        return item.id;
    }
    registerChangeInCuaHangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cuaHangListModification',
            response => this.loadAll()
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    filterCuaHangs(event: any) {
        const query = event.query;
        console.log(query);
        this.cuaHangService.getCuaHangs(query).subscribe((cuahangs: any) => {
            this.filteredCuaHangs = this.filterCuaHang(query, cuahangs);
        });
    }
    filterCuaHang(query: any, cuahangs: CuaHang[]): CuaHang[] {
        const filtered: any[] = [];
        for (const CuaHang of cuahangs) {
            if (CuaHang.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(CuaHang);
            }
        }
        return filtered;
    }
}
