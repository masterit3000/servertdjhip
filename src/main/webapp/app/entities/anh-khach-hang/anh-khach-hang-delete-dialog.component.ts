import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnhKhachHang } from './anh-khach-hang.model';
import { AnhKhachHangPopupService } from './anh-khach-hang-popup.service';
import { AnhKhachHangService } from './anh-khach-hang.service';

@Component({
    selector: 'jhi-anh-khach-hang-delete-dialog',
    templateUrl: './anh-khach-hang-delete-dialog.component.html'
})
export class AnhKhachHangDeleteDialogComponent {

    anhKhachHang: AnhKhachHang;

    constructor(
        private anhKhachHangService: AnhKhachHangService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anhKhachHangService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anhKhachHangListModification',
                content: 'Deleted an anhKhachHang'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anh-khach-hang-delete-popup',
    template: ''
})
export class AnhKhachHangDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anhKhachHangPopupService: AnhKhachHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.anhKhachHangPopupService
                .open(AnhKhachHangDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
