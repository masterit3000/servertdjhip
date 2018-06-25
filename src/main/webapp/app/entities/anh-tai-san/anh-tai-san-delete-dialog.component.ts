import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnhTaiSan } from './anh-tai-san.model';
import { AnhTaiSanPopupService } from './anh-tai-san-popup.service';
import { AnhTaiSanService } from './anh-tai-san.service';

@Component({
    selector: 'jhi-anh-tai-san-delete-dialog',
    templateUrl: './anh-tai-san-delete-dialog.component.html'
})
export class AnhTaiSanDeleteDialogComponent {

    anhTaiSan: AnhTaiSan;

    constructor(
        private anhTaiSanService: AnhTaiSanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anhTaiSanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anhTaiSanListModification',
                content: 'Deleted an anhTaiSan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anh-tai-san-delete-popup',
    template: ''
})
export class AnhTaiSanDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anhTaiSanPopupService: AnhTaiSanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.anhTaiSanPopupService
                .open(AnhTaiSanDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
