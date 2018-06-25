import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { LichSuThaoTacHopDongService } from './lich-su-thao-tac-hop-dong.service';

@Injectable()
export class LichSuThaoTacHopDongPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService

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
                this.lichSuThaoTacHopDongService.find(id)
                    .subscribe((lichSuThaoTacHopDongResponse: HttpResponse<LichSuThaoTacHopDong>) => {
                        const lichSuThaoTacHopDong: LichSuThaoTacHopDong = lichSuThaoTacHopDongResponse.body;
                        lichSuThaoTacHopDong.thoigian = this.datePipe
                            .transform(lichSuThaoTacHopDong.thoigian, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.lichSuThaoTacHopDongModalRef(component, lichSuThaoTacHopDong);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lichSuThaoTacHopDongModalRef(component, new LichSuThaoTacHopDong());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lichSuThaoTacHopDongModalRef(component: Component, lichSuThaoTacHopDong: LichSuThaoTacHopDong): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lichSuThaoTacHopDong = lichSuThaoTacHopDong;
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
