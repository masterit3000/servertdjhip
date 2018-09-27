import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Images } from './images.model';
import { ImagesService } from './images.service';

@Component({
    selector: 'jhi-images-detail',
    templateUrl: './images-detail.component.html'
})
export class ImagesDetailComponent implements OnInit, OnDestroy {

    images: Images;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private imagesService: ImagesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImages();
    }

    load(id) {
        this.imagesService.find(id)
            .subscribe((imagesResponse: HttpResponse<Images>) => {
                this.images = imagesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imagesListModification',
            (response) => this.load(this.images.id)
        );
    }
}
