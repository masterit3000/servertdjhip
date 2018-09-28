import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    ImagesService,
    ImagesPopupService,
    ImagesComponent,
    ImagesDetailComponent,
    ImagesDialogComponent,
    ImagesPopupComponent,
    ImagesDeletePopupComponent,
    ImagesDeleteDialogComponent,
    imagesRoute,
    imagesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...imagesRoute,
    ...imagesPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ImagesComponent,
        ImagesDetailComponent,
        ImagesDialogComponent,
        ImagesDeleteDialogComponent,
        ImagesPopupComponent,
        ImagesDeletePopupComponent,
    ],
    entryComponents: [
        ImagesComponent,
        ImagesDialogComponent,
        ImagesPopupComponent,
        ImagesDeleteDialogComponent,
        ImagesDeletePopupComponent,
    ],
    providers: [
        ImagesService,
        ImagesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipImagesModule {}
