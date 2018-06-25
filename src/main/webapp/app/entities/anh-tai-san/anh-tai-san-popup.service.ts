import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AnhTaiSan } from './anh-tai-san.model';
import { AnhTaiSanService } from './anh-tai-san.service';

@Injectable()
export class AnhTaiSanPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private anhTaiSanService: AnhTaiSanService

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
                this.anhTaiSanService.find(id)
                    .subscribe((anhTaiSanResponse: HttpResponse<AnhTaiSan>) => {
                        const anhTaiSan: AnhTaiSan = anhTaiSanResponse.body;
                        this.ngbModalRef = this.anhTaiSanModalRef(component, anhTaiSan);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.anhTaiSanModalRef(component, new AnhTaiSan());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    anhTaiSanModalRef(component: Component, anhTaiSan: AnhTaiSan): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.anhTaiSan = anhTaiSan;
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
