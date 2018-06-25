import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { CuaHangPopupService } from './cua-hang-popup.service';
import { CuaHangService } from './cua-hang.service';

@Component({
    selector: 'jhi-cua-hang-delete-dialog',
    templateUrl: './cua-hang-delete-dialog.component.html'
})
export class CuaHangDeleteDialogComponent {

    cuaHang: CuaHang;

    constructor(
        private cuaHangService: CuaHangService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cuaHangService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cuaHangListModification',
                content: 'Deleted an cuaHang'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cua-hang-delete-popup',
    template: ''
})
export class CuaHangDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cuaHangPopupService: CuaHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cuaHangPopupService
                .open(CuaHangDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
