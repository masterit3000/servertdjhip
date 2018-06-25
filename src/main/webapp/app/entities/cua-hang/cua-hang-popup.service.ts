import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { CuaHang } from './cua-hang.model';
import { CuaHangService } from './cua-hang.service';

@Injectable()
export class CuaHangPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private cuaHangService: CuaHangService

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
                this.cuaHangService.find(id)
                    .subscribe((cuaHangResponse: HttpResponse<CuaHang>) => {
                        const cuaHang: CuaHang = cuaHangResponse.body;
                        cuaHang.ngayTao = this.datePipe
                            .transform(cuaHang.ngayTao, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.cuaHangModalRef(component, cuaHang);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.cuaHangModalRef(component, new CuaHang());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    cuaHangModalRef(component: Component, cuaHang: CuaHang): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cuaHang = cuaHang;
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
