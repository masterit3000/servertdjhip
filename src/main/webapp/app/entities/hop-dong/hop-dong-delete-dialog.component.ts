import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopDong } from './hop-dong.model';
import { HopDongPopupService } from './hop-dong-popup.service';
import { HopDongService } from './hop-dong.service';

@Component({
    selector: 'jhi-hop-dong-delete-dialog',
    templateUrl: './hop-dong-delete-dialog.component.html'
})
export class HopDongDeleteDialogComponent {

    hopDong: HopDong;

    constructor(
        private hopDongService: HopDongService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopDongService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopDongListModification',
                content: 'Deleted an hopDong'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-dong-delete-popup',
    template: ''
})
export class HopDongDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDongPopupService: HopDongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopDongPopupService
                .open(HopDongDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
