import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { NhanVien } from './nhan-vien.model';
import { NhanVienService } from './nhan-vien.service';

@Injectable()
export class NhanVienPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private nhanVienService: NhanVienService

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
                this.nhanVienService.find(id)
                    .subscribe((nhanVienResponse: HttpResponse<NhanVien>) => {
                        const nhanVien: NhanVien = nhanVienResponse.body;
                        nhanVien.ngayTao = this.datePipe
                            .transform(nhanVien.ngayTao, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.nhanVienModalRef(component, nhanVien);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nhanVienModalRef(component, new NhanVien());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nhanVienModalRef(component: Component, nhanVien: NhanVien): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nhanVien = nhanVien;
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
