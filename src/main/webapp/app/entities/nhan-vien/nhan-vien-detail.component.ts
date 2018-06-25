import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NhanVien } from './nhan-vien.model';
import { NhanVienService } from './nhan-vien.service';

@Component({
    selector: 'jhi-nhan-vien-detail',
    templateUrl: './nhan-vien-detail.component.html'
})
export class NhanVienDetailComponent implements OnInit, OnDestroy {

    nhanVien: NhanVien;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nhanVienService: NhanVienService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNhanViens();
    }

    load(id) {
        this.nhanVienService.find(id)
            .subscribe((nhanVienResponse: HttpResponse<NhanVien>) => {
                this.nhanVien = nhanVienResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNhanViens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nhanVienListModification',
            (response) => this.load(this.nhanVien.id)
        );
    }
}
