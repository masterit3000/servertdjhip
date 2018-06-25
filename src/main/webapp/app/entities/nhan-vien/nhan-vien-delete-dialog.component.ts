import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NhanVien } from './nhan-vien.model';
import { NhanVienPopupService } from './nhan-vien-popup.service';
import { NhanVienService } from './nhan-vien.service';

@Component({
    selector: 'jhi-nhan-vien-delete-dialog',
    templateUrl: './nhan-vien-delete-dialog.component.html'
})
export class NhanVienDeleteDialogComponent {

    nhanVien: NhanVien;

    constructor(
        private nhanVienService: NhanVienService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nhanVienService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nhanVienListModification',
                content: 'Deleted an nhanVien'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nhan-vien-delete-popup',
    template: ''
})
export class NhanVienDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nhanVienPopupService: NhanVienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nhanVienPopupService
                .open(NhanVienDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
