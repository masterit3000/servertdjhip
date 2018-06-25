import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { GhiNo } from './ghi-no.model';
import { GhiNoService } from './ghi-no.service';

@Injectable()
export class GhiNoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private ghiNoService: GhiNoService

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
                this.ghiNoService.find(id)
                    .subscribe((ghiNoResponse: HttpResponse<GhiNo>) => {
                        const ghiNo: GhiNo = ghiNoResponse.body;
                        ghiNo.ngayghino = this.datePipe
                            .transform(ghiNo.ngayghino, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.ghiNoModalRef(component, ghiNo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ghiNoModalRef(component, new GhiNo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ghiNoModalRef(component: Component, ghiNo: GhiNo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ghiNo = ghiNo;
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
