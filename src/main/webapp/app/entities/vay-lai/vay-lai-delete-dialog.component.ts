import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VayLai } from './vay-lai.model';
import { VayLaiPopupService } from './vay-lai-popup.service';
import { VayLaiService } from './vay-lai.service';

@Component({
    selector: 'jhi-vay-lai-delete-dialog',
    templateUrl: './vay-lai-delete-dialog.component.html'
})
export class VayLaiDeleteDialogComponent {

    vayLai: VayLai;

    constructor(
        private vayLaiService: VayLaiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vayLaiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vayLaiListModification',
                content: 'Deleted an vayLai'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vay-lai-delete-popup',
    template: ''
})
export class VayLaiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vayLaiPopupService: VayLaiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vayLaiPopupService
                .open(VayLaiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
