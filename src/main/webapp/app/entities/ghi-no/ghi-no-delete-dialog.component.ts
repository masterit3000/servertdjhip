import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { GhiNoPopupService } from './ghi-no-popup.service';
import { GhiNoService } from './ghi-no.service';

@Component({
    selector: 'jhi-ghi-no-delete-dialog',
    templateUrl: './ghi-no-delete-dialog.component.html'
})
export class GhiNoDeleteDialogComponent {

    ghiNo: GhiNo;

    constructor(
        private ghiNoService: GhiNoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ghiNoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ghiNoListModification',
                content: 'Deleted an ghiNo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ghi-no-delete-popup',
    template: ''
})
export class GhiNoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ghiNoPopupService: GhiNoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ghiNoPopupService
                .open(GhiNoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
