import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Xa } from './xa.model';
import { XaPopupService } from './xa-popup.service';
import { XaService } from './xa.service';

@Component({
    selector: 'jhi-xa-delete-dialog',
    templateUrl: './xa-delete-dialog.component.html'
})
export class XaDeleteDialogComponent {

    xa: Xa;

    constructor(
        private xaService: XaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.xaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'xaListModification',
                content: 'Deleted an xa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-xa-delete-popup',
    template: ''
})
export class XaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private xaPopupService: XaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.xaPopupService
                .open(XaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
