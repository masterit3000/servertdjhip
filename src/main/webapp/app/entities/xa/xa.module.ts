import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    XaService,
    XaPopupService,
    XaComponent,
    XaDetailComponent,
    XaDialogComponent,
    XaPopupComponent,
    XaDeletePopupComponent,
    XaDeleteDialogComponent,
    xaRoute,
    xaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...xaRoute,
    ...xaPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        XaComponent,
        XaDetailComponent,
        XaDialogComponent,
        XaDeleteDialogComponent,
        XaPopupComponent,
        XaDeletePopupComponent,
    ],
    entryComponents: [
        XaComponent,
        XaDialogComponent,
        XaPopupComponent,
        XaDeleteDialogComponent,
        XaDeletePopupComponent,
    ],
    providers: [
        XaService,
        XaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipXaModule {}
