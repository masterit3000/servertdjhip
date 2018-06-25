import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { KhachHangPopupService } from './khach-hang-popup.service';
import { KhachHangService } from './khach-hang.service';

@Component({
    selector: 'jhi-khach-hang-delete-dialog',
    templateUrl: './khach-hang-delete-dialog.component.html'
})
export class KhachHangDeleteDialogComponent {

    khachHang: KhachHang;

    constructor(
        private khachHangService: KhachHangService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.khachHangService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'khachHangListModification',
                content: 'Deleted an khachHang'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-khach-hang-delete-popup',
    template: ''
})
export class KhachHangDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private khachHangPopupService: KhachHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.khachHangPopupService
                .open(KhachHangDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
