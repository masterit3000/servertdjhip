import { Component, OnInit } from '@angular/core';
import { KhachHang, KhachHangService } from '../../../khach-hang';
import { Principal } from '../../../../shared';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VayLaiService } from '../../vay-lai.service';
import { VayLai } from '../../vay-lai.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { HopDong } from '../../../hop-dong';

@Component({
    selector: 'jhi-vay-lai-moi',
    templateUrl: './vay-lai-moi.component.html',
    styles: []
})
export class VayLaiMoiComponent implements OnInit {
    khachHangs: KhachHang[]; // chua danh sach khach hang
    vayLai: VayLai; // vay lai hien tai biding voi thong tin vay lai
    keyTimKhachHang: String;
    khachhangid: any;
    mahopdong: any;

    constructor(
        private khachHangService: KhachHangService,
        private VayLaiService: VayLaiService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.vayLai = new VayLai();
        this.vayLai.hopdongvl = new HopDong();
        this.vayLai.hopdongvl.khachHangId = 0;
        // this.VayLai.hopdong.mahopdong ='';
        // this.VayLai.hopdong.khachHangId = null;

        // this.VayLai.hopdong = null;
    }

    ngOnInit() {}
    save() {
        console.log(this.vayLai);

        this.VayLaiService.create(this.vayLai).subscribe(
            (res: HttpResponse<VayLai>) => {
                this.jhiAlertService.info(
                    'them vay lai thanh cong',
                    null,
                    null
                );
            }, // thanh cong thi goi
            (err: HttpErrorResponse) => {
                console.log(err);
                this.jhiAlertService.error(err.message, null, null);
            } // loi thi goi ham nay
        );

        // VayLai.hopdong.khachangid = this.khachhangid;
        // VayLai.hopdong.mahopdong = this.mahopdong;
        // this.VayLaiService.cre
    }

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

    private onSelectionChange(khachangid) {
        this.vayLai.hopdongvl.khachHangId = khachangid;
    }
}
