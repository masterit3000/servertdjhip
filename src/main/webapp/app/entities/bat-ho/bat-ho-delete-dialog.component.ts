import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BatHo } from './bat-ho.model';
import { BatHoPopupService } from './bat-ho-popup.service';
import { BatHoService } from './bat-ho.service';

@Component({
    selector: 'jhi-bat-ho-delete-dialog',
    templateUrl: './bat-ho-delete-dialog.component.html'
})
export class BatHoDeleteDialogComponent {

    batHo: BatHo;

    constructor(
        private batHoService: BatHoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.batHoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'batHoListModification',
                content: 'Deleted an batHo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bat-ho-delete-popup',
    template: ''
})
export class BatHoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private batHoPopupService: BatHoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.batHoPopupService
                .open(BatHoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
