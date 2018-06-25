import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tinh } from './tinh.model';
import { TinhPopupService } from './tinh-popup.service';
import { TinhService } from './tinh.service';

@Component({
    selector: 'jhi-tinh-delete-dialog',
    templateUrl: './tinh-delete-dialog.component.html'
})
export class TinhDeleteDialogComponent {

    tinh: Tinh;

    constructor(
        private tinhService: TinhService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tinhService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tinhListModification',
                content: 'Deleted an tinh'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tinh-delete-popup',
    template: ''
})
export class TinhDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tinhPopupService: TinhPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tinhPopupService
                .open(TinhDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
