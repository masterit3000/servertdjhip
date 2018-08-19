import { Component, OnInit } from '@angular/core';
import { KhachHang, KhachHangService } from '../../../khach-hang';
import { Principal } from '../../../../shared';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { BatHoService } from '../../bat-ho.service';
import { BatHo } from '../../bat-ho.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { HopDong } from '../../../hop-dong';
import { Observable } from '../../../../../../../../node_modules/rxjs/Observable';
import { Subscription } from '../../../../../../../../node_modules/rxjs';

@Component({
    selector: 'jhi-bat-ho-moi',
    templateUrl: './bat-ho-moi.component.html',
    styles: []
})
export class BatHoMoiComponent implements OnInit {
    khachHangs: KhachHang[]; // chua danh sach khach hang
    batHo: BatHo; // bat ho hien tai biding voi thong tin bat ho
    keyTimKhachHang: String;
    khachhangid: any;
    mahopdong: any;
    isSaving: boolean;
    eventSubscriber: Subscription;
    selected: KhachHang;
    constructor(
        private khachHangService: KhachHangService,
        private batHoService: BatHoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
    ) {
        this.batHo = new BatHo();
        this.batHo.hopdong = new HopDong();
        this.batHo.hopdong.khachHangId = 0;
        // this.batHo.hopdong.mahopdong ='';
        // this.batHo.hopdong.khachHangId = null;

        // this.batHo.hopdong = null;
    }

    clear() {
        this.previousState();
    }
    ngOnInit() {

        this.eventSubscriber = this.eventManager // lưu toàn bộ việc theo dõi sự kiện vào 1 biến để tẹo hủy theo dõi (dòng 48)
        .subscribe('khachHangListModification', response => {
            // đăng ký lắng nghe sự kiện có tên khachHangListModification
            // khi sự kện khachHangListModification nổ ra sẽ chạy hàm dưới, response là dữ liệu mà sự kiện nổ ra truyền vào
            //this.loadAll(); // load lại data
            // let kh : KhachHang = response;
            console.log(response); // in ra xem sự kiện nổ ra truyền vào cái j
            this.keyTimKhachHang = response.content.cmnd;
            this.batHo.hopdong.khachHangId = response.content.id;
            // this.timKhachHang();
        });
    }
    // save() {
    //     console.log(this.batHo);
    //     this.bathoService.create(this.batHo).subscribe(
    //         (res: HttpResponse<BatHo>) => {
    //             this.jhiAlertService.info('them bat ho thanh cong', null, null);
    //             this
    //         }, // thanh cong thi goi
    //         (err: HttpErrorResponse) => {
    //             console.log(err);
    //             this.jhiAlertService.error(err.message, null, null);
    //         } // loi thi goi ham nay
    //     );

        // batHo.hopdong.khachangid = this.khachhangid;
        // batHo.hopdong.mahopdong = this.mahopdong;
        // this.bathoService.cre
    // }
    save() {
        this.isSaving = true;
        if (this.batHo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.batHoService.update(this.batHo));
        } else {
            this.subscribeToSaveResponse(
                this.batHoService.create(this.batHo));
        }
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<BatHo>>) {
        result.subscribe((res: HttpResponse<BatHo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }
    private onSaveSuccess(result: BatHo) {
        this.eventManager.broadcast({ name: 'batHoListModification', content: 'OK'});
        this.isSaving = false;
        // this.activeModal.dismiss(result);
        this.jhiAlertService.success('them moi thanh cong', null, null);
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
        this.jhiAlertService.success('them moi that bai', null, null);
    }
    previousState() {
        window.history.back();
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

    onRowSelect(event) {
        this.batHo.hopdong.khachHangId = event.data.id;
    }
}
