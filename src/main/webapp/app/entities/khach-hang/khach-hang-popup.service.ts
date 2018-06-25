import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { KhachHang } from './khach-hang.model';
import { KhachHangService } from './khach-hang.service';

@Injectable()
export class KhachHangPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private khachHangService: KhachHangService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.khachHangService.find(id)
                    .subscribe((khachHangResponse: HttpResponse<KhachHang>) => {
                        const khachHang: KhachHang = khachHangResponse.body;
                        khachHang.ngayTao = this.datePipe
                            .transform(khachHang.ngayTao, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.khachHangModalRef(component, khachHang);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.khachHangModalRef(component, new KhachHang());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    khachHangModalRef(component: Component, khachHang: KhachHang): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.khachHang = khachHang;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
