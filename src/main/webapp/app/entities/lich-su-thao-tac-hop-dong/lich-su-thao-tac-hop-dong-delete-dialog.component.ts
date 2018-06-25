import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { LichSuThaoTacHopDongPopupService } from './lich-su-thao-tac-hop-dong-popup.service';
import { LichSuThaoTacHopDongService } from './lich-su-thao-tac-hop-dong.service';

@Component({
    selector: 'jhi-lich-su-thao-tac-hop-dong-delete-dialog',
    templateUrl: './lich-su-thao-tac-hop-dong-delete-dialog.component.html'
})
export class LichSuThaoTacHopDongDeleteDialogComponent {

    lichSuThaoTacHopDong: LichSuThaoTacHopDong;

    constructor(
        private lichSuThaoTacHopDongService: LichSuThaoTacHopDongService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lichSuThaoTacHopDongService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lichSuThaoTacHopDongListModification',
                content: 'Deleted an lichSuThaoTacHopDong'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lich-su-thao-tac-hop-dong-delete-popup',
    template: ''
})
export class LichSuThaoTacHopDongDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lichSuThaoTacHopDongPopupService: LichSuThaoTacHopDongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lichSuThaoTacHopDongPopupService
                .open(LichSuThaoTacHopDongDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
