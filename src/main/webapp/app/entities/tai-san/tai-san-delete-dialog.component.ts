import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TaiSan } from './tai-san.model';
import { TaiSanPopupService } from './tai-san-popup.service';
import { TaiSanService } from './tai-san.service';

@Component({
    selector: 'jhi-tai-san-delete-dialog',
    templateUrl: './tai-san-delete-dialog.component.html'
})
export class TaiSanDeleteDialogComponent {

    taiSan: TaiSan;

    constructor(
        private taiSanService: TaiSanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taiSanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taiSanListModification',
                content: 'Deleted an taiSan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tai-san-delete-popup',
    template: ''
})
export class TaiSanDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taiSanPopupService: TaiSanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.taiSanPopupService
                .open(TaiSanDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
