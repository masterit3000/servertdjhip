import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { LichSuDongTien } from './lich-su-dong-tien.model';
import { LichSuDongTienService } from './lich-su-dong-tien.service';

@Injectable()
export class LichSuDongTienPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private lichSuDongTienService: LichSuDongTienService

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
                this.lichSuDongTienService.find(id)
                    .subscribe((lichSuDongTienResponse: HttpResponse<LichSuDongTien>) => {
                        const lichSuDongTien: LichSuDongTien = lichSuDongTienResponse.body;
                        lichSuDongTien.ngaybatdau = this.datePipe
                            .transform(lichSuDongTien.ngaybatdau, 'yyyy-MM-ddTHH:mm:ss');
                        lichSuDongTien.ngayketthuc = this.datePipe
                            .transform(lichSuDongTien.ngayketthuc, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.lichSuDongTienModalRef(component, lichSuDongTien);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lichSuDongTienModalRef(component, new LichSuDongTien());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lichSuDongTienModalRef(component: Component, lichSuDongTien: LichSuDongTien): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lichSuDongTien = lichSuDongTien;
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
