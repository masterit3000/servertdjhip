import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { NhatKy } from './nhat-ky.model';
import { NhatKyService } from './nhat-ky.service';

@Injectable()
export class NhatKyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private nhatKyService: NhatKyService

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
                this.nhatKyService.find(id)
                    .subscribe((nhatKyResponse: HttpResponse<NhatKy>) => {
                        const nhatKy: NhatKy = nhatKyResponse.body;
                        nhatKy.thoiGian = this.datePipe
                            .transform(nhatKy.thoiGian, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.nhatKyModalRef(component, nhatKy);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nhatKyModalRef(component, new NhatKy());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nhatKyModalRef(component: Component, nhatKy: NhatKy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nhatKy = nhatKy;
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
