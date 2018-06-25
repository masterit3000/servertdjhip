import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { KhachHangService } from './khach-hang.service';

@Component({
    selector: 'jhi-khach-hang-detail',
    templateUrl: './khach-hang-detail.component.html'
})
export class KhachHangDetailComponent implements OnInit, OnDestroy {

    khachHang: KhachHang;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private khachHangService: KhachHangService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKhachHangs();
    }

    load(id) {
        this.khachHangService.find(id)
            .subscribe((khachHangResponse: HttpResponse<KhachHang>) => {
                this.khachHang = khachHangResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKhachHangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'khachHangListModification',
            (response) => this.load(this.khachHang.id)
        );
    }
}
