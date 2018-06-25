import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ThuChi } from './thu-chi.model';
import { ThuChiPopupService } from './thu-chi-popup.service';
import { ThuChiService } from './thu-chi.service';

@Component({
    selector: 'jhi-thu-chi-delete-dialog',
    templateUrl: './thu-chi-delete-dialog.component.html'
})
export class ThuChiDeleteDialogComponent {

    thuChi: ThuChi;

    constructor(
        private thuChiService: ThuChiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.thuChiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'thuChiListModification',
                content: 'Deleted an thuChi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-thu-chi-delete-popup',
    template: ''
})
export class ThuChiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thuChiPopupService: ThuChiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.thuChiPopupService
                .open(ThuChiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
