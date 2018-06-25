import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LichSuDongTien } from './lich-su-dong-tien.model';
import { LichSuDongTienPopupService } from './lich-su-dong-tien-popup.service';
import { LichSuDongTienService } from './lich-su-dong-tien.service';

@Component({
    selector: 'jhi-lich-su-dong-tien-delete-dialog',
    templateUrl: './lich-su-dong-tien-delete-dialog.component.html'
})
export class LichSuDongTienDeleteDialogComponent {

    lichSuDongTien: LichSuDongTien;

    constructor(
        private lichSuDongTienService: LichSuDongTienService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lichSuDongTienService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lichSuDongTienListModification',
                content: 'Deleted an lichSuDongTien'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lich-su-dong-tien-delete-popup',
    template: ''
})
export class LichSuDongTienDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lichSuDongTienPopupService: LichSuDongTienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lichSuDongTienPopupService
                .open(LichSuDongTienDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
