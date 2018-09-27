import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Images } from './images.model';
import { ImagesPopupService } from './images-popup.service';
import { ImagesService } from './images.service';

@Component({
    selector: 'jhi-images-delete-dialog',
    templateUrl: './images-delete-dialog.component.html'
})
export class ImagesDeleteDialogComponent {

    images: Images;

    constructor(
        private imagesService: ImagesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imagesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imagesListModification',
                content: 'Deleted an images'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-images-delete-popup',
    template: ''
})
export class ImagesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagesPopupService: ImagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imagesPopupService
                .open(ImagesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
