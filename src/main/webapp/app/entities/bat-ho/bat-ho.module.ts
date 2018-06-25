import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    BatHoService,
    BatHoPopupService,
    BatHoComponent,
    BatHoDetailComponent,
    BatHoDialogComponent,
    BatHoPopupComponent,
    BatHoDeletePopupComponent,
    BatHoDeleteDialogComponent,
    batHoRoute,
    batHoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...batHoRoute,
    ...batHoPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BatHoComponent,
        BatHoDetailComponent,
        BatHoDialogComponent,
        BatHoDeleteDialogComponent,
        BatHoPopupComponent,
        BatHoDeletePopupComponent,
    ],
    entryComponents: [
        BatHoComponent,
        BatHoDialogComponent,
        BatHoPopupComponent,
        BatHoDeleteDialogComponent,
        BatHoDeletePopupComponent,
    ],
    providers: [
        BatHoService,
        BatHoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipBatHoModule {}
