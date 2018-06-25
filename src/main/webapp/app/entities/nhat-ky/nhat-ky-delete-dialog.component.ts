import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NhatKy } from './nhat-ky.model';
import { NhatKyPopupService } from './nhat-ky-popup.service';
import { NhatKyService } from './nhat-ky.service';

@Component({
    selector: 'jhi-nhat-ky-delete-dialog',
    templateUrl: './nhat-ky-delete-dialog.component.html'
})
export class NhatKyDeleteDialogComponent {

    nhatKy: NhatKy;

    constructor(
        private nhatKyService: NhatKyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nhatKyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nhatKyListModification',
                content: 'Deleted an nhatKy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nhat-ky-delete-popup',
    template: ''
})
export class NhatKyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nhatKyPopupService: NhatKyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nhatKyPopupService
                .open(NhatKyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
