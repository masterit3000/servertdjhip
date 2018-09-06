import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { KhachHangService } from './khach-hang.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-khach-hang',
    templateUrl: './khach-hang.component.html',
    styles: []
})
export class KhachHangComponent implements OnInit, OnDestroy {
    khachHangs: KhachHang[];
    khachHangsInSystem: KhachHang[];
    currentAccount: any;
    eventSubscriber: Subscription;
    filteredKhachHangs: KhachHang[];
    text: any;
    khachHang: any;
    none: any;
    keyTimKhachHang: String;
    keyKhachHangInSystem: String;
    constructor(
        private khachHangService: KhachHangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}
    timKhachHang() {
        // const query = event.query;
        // console.log(query);
        this.khachHangService
            .findKhachHangByTenOrCMND(this.keyTimKhachHang)
            .subscribe(
                (res: HttpResponse<KhachHang[]>) => {
                    this.khachHangs = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    kiemTraTrongHeThong() {
        // const query = event.query;
        // console.log(query);
        this.khachHangService
            .findInSystem(this.keyKhachHangInSystem)
            .subscribe(
                (res: HttpResponse<KhachHang[]>) => {
                    this.khachHangsInSystem = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    loadAll() {
        this.khachHangService.query().subscribe(
            (res: HttpResponse<KhachHang[]>) => {
                this.khachHangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKhachHangs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KhachHang) {
        return item.id;
    }

    registerChangeInKhachHangs() {
        this.eventSubscriber = this.eventManager // lưu toàn bộ việc theo dõi sự kiện vào 1 biến để tẹo hủy theo dõi (dòng 48)
            .subscribe('khachHangListModification', response => {
                // đăng ký lắng nghe sự kiện có tên khachHangListModification
                // khi sự kện khachHangListModification nổ ra sẽ chạy hàm dưới, response là dữ liệu mà sự kiện nổ ra truyền vào
                this.loadAll(); // load lại data
                console.log(response); // in ra xem sự kiện nổ ra truyền vào cái j
            });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    filterKhachHangs(event: any) {
        const query = event.query;
        console.log(query);
        this.khachHangService
            .query(query)
            .subscribe((khachHangs: any) => {
                this.filteredKhachHangs = khachHangs;
            });
    }
}
