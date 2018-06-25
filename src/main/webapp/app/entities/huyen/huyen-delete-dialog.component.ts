import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Huyen } from './huyen.model';
import { HuyenPopupService } from './huyen-popup.service';
import { HuyenService } from './huyen.service';

@Component({
    selector: 'jhi-huyen-delete-dialog',
    templateUrl: './huyen-delete-dialog.component.html'
})
export class HuyenDeleteDialogComponent {

    huyen: Huyen;

    constructor(
        private huyenService: HuyenService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.huyenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'huyenListModification',
                content: 'Deleted an huyen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-huyen-delete-popup',
    template: ''
})
export class HuyenDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private huyenPopupService: HuyenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.huyenPopupService
                .open(HuyenDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
