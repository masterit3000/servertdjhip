import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ThuChi } from './thu-chi.model';
import { ThuChiService } from './thu-chi.service';

@Injectable()
export class ThuChiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private thuChiService: ThuChiService

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
                this.thuChiService.find(id)
                    .subscribe((thuChiResponse: HttpResponse<ThuChi>) => {
                        const thuChi: ThuChi = thuChiResponse.body;
                        thuChi.thoigian = this.datePipe
                            .transform(thuChi.thoigian, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.thuChiModalRef(component, thuChi);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.thuChiModalRef(component, new ThuChi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    thuChiModalRef(component: Component, thuChi: ThuChi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.thuChi = thuChi;
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
