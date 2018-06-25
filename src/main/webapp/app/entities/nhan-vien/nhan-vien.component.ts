import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NhanVien } from './nhan-vien.model';
import { NhanVienService } from './nhan-vien.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-nhan-vien',
    templateUrl: './nhan-vien.component.html'
})
export class NhanVienComponent implements OnInit, OnDestroy {
nhanViens: NhanVien[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nhanVienService: NhanVienService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nhanVienService.query().subscribe(
            (res: HttpResponse<NhanVien[]>) => {
                this.nhanViens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNhanViens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NhanVien) {
        return item.id;
    }
    registerChangeInNhanViens() {
        this.eventSubscriber = this.eventManager.subscribe('nhanVienListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
