import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { HopDong } from './hop-dong.model';
import { HopDongService } from './hop-dong.service';

@Injectable()
export class HopDongPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private hopDongService: HopDongService

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
                this.hopDongService.find(id)
                    .subscribe((hopDongResponse: HttpResponse<HopDong>) => {
                        const hopDong: HopDong = hopDongResponse.body;
                        hopDong.ngaytao = this.datePipe
                            .transform(hopDong.ngaytao, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.hopDongModalRef(component, hopDong);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopDongModalRef(component, new HopDong());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopDongModalRef(component: Component, hopDong: HopDong): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopDong = hopDong;
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
