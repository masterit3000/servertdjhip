import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { CuaHangService } from './cua-hang.service';

@Component({
    selector: 'jhi-cua-hang-detail',
    templateUrl: './cua-hang-detail.component.html'
})
export class CuaHangDetailComponent implements OnInit, OnDestroy {

    cuaHang: CuaHang;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cuaHangService: CuaHangService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCuaHangs();
    }

    load(id) {
        this.cuaHangService.find(id)
            .subscribe((cuaHangResponse: HttpResponse<CuaHang>) => {
                this.cuaHang = cuaHangResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCuaHangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cuaHangListModification',
            (response) => this.load(this.cuaHang.id)
        );
    }
}
