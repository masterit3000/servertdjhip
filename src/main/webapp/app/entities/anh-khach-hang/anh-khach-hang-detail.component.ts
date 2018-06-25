import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AnhKhachHang } from './anh-khach-hang.model';
import { AnhKhachHangService } from './anh-khach-hang.service';

@Component({
    selector: 'jhi-anh-khach-hang-detail',
    templateUrl: './anh-khach-hang-detail.component.html'
})
export class AnhKhachHangDetailComponent implements OnInit, OnDestroy {

    anhKhachHang: AnhKhachHang;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private anhKhachHangService: AnhKhachHangService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnhKhachHangs();
    }

    load(id) {
        this.anhKhachHangService.find(id)
            .subscribe((anhKhachHangResponse: HttpResponse<AnhKhachHang>) => {
                this.anhKhachHang = anhKhachHangResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnhKhachHangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anhKhachHangListModification',
            (response) => this.load(this.anhKhachHang.id)
        );
    }
}
