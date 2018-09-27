import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Images } from './images.model';
import { ImagesPopupService } from './images-popup.service';
import { ImagesService } from './images.service';

@Component({
    selector: 'jhi-images-dialog',
    templateUrl: './images-dialog.component.html'
})
export class ImagesDialogComponent implements OnInit {

    images: Images;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private imagesService: ImagesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.images.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imagesService.update(this.images));
        } else {
            this.subscribeToSaveResponse(
                this.imagesService.create(this.images));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Images>>) {
        result.subscribe((res: HttpResponse<Images>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Images) {
        this.eventManager.broadcast({ name: 'imagesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-images-popup',
    template: ''
})
export class ImagesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagesPopupService: ImagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imagesPopupService
                    .open(ImagesDialogComponent as Component, params['id']);
            } else {
                this.imagesPopupService
                    .open(ImagesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
