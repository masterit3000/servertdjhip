import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    VayLaiService,
    VayLaiPopupService,
    VayLaiComponent,
    VayLaiDetailComponent,
    VayLaiDialogComponent,
    VayLaiPopupComponent,
    VayLaiDeletePopupComponent,
    VayLaiDeleteDialogComponent,
    vayLaiRoute,
    vayLaiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vayLaiRoute,
    ...vayLaiPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VayLaiComponent,
        VayLaiDetailComponent,
        VayLaiDialogComponent,
        VayLaiDeleteDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeletePopupComponent,
    ],
    entryComponents: [
        VayLaiComponent,
        VayLaiDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeleteDialogComponent,
        VayLaiDeletePopupComponent,
    ],
    providers: [
        VayLaiService,
        VayLaiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipVayLaiModule {}
