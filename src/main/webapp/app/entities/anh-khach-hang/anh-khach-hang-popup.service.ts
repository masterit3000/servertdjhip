import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AnhKhachHang } from './anh-khach-hang.model';
import { AnhKhachHangService } from './anh-khach-hang.service';

@Injectable()
export class AnhKhachHangPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private anhKhachHangService: AnhKhachHangService

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
                this.anhKhachHangService.find(id)
                    .subscribe((anhKhachHangResponse: HttpResponse<AnhKhachHang>) => {
                        const anhKhachHang: AnhKhachHang = anhKhachHangResponse.body;
                        this.ngbModalRef = this.anhKhachHangModalRef(component, anhKhachHang);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.anhKhachHangModalRef(component, new AnhKhachHang());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    anhKhachHangModalRef(component: Component, anhKhachHang: AnhKhachHang): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.anhKhachHang = anhKhachHang;
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
