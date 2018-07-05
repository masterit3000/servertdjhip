import { Component, OnInit } from '@angular/core';
import { KhachHang, KhachHangService } from '../../../khach-hang';
import { Principal } from '../../../../shared';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { BatHoService } from '../../bat-ho.service';
import { BatHo } from '../../bat-ho.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-bat-ho-moi',
    templateUrl: './bat-ho-moi.component.html',
    styles: []
})
export class BatHoMoiComponent implements OnInit {

    khachHangs: KhachHang[]; // chua danh sach khach hang
    batHo: BatHo; // bat ho hien tai biding voi thong tin bat ho
    keyTimKhachHang: String;
    constructor(
        private khachHangService: KhachHangService,
        private bathoService: BatHoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal

    ) {


    }

    ngOnInit() {

    }
    save() { }

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
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
