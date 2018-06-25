import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    NhatKyService,
    NhatKyPopupService,
    NhatKyComponent,
    NhatKyDetailComponent,
    NhatKyDialogComponent,
    NhatKyPopupComponent,
    NhatKyDeletePopupComponent,
    NhatKyDeleteDialogComponent,
    nhatKyRoute,
    nhatKyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nhatKyRoute,
    ...nhatKyPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NhatKyComponent,
        NhatKyDetailComponent,
        NhatKyDialogComponent,
        NhatKyDeleteDialogComponent,
        NhatKyPopupComponent,
        NhatKyDeletePopupComponent,
    ],
    entryComponents: [
        NhatKyComponent,
        NhatKyDialogComponent,
        NhatKyPopupComponent,
        NhatKyDeleteDialogComponent,
        NhatKyDeletePopupComponent,
    ],
    providers: [
        NhatKyService,
        NhatKyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipNhatKyModule {}
