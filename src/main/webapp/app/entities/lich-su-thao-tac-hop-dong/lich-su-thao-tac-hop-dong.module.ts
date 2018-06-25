import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    LichSuThaoTacHopDongService,
    LichSuThaoTacHopDongPopupService,
    LichSuThaoTacHopDongComponent,
    LichSuThaoTacHopDongDetailComponent,
    LichSuThaoTacHopDongDialogComponent,
    LichSuThaoTacHopDongPopupComponent,
    LichSuThaoTacHopDongDeletePopupComponent,
    LichSuThaoTacHopDongDeleteDialogComponent,
    lichSuThaoTacHopDongRoute,
    lichSuThaoTacHopDongPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lichSuThaoTacHopDongRoute,
    ...lichSuThaoTacHopDongPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LichSuThaoTacHopDongComponent,
        LichSuThaoTacHopDongDetailComponent,
        LichSuThaoTacHopDongDialogComponent,
        LichSuThaoTacHopDongDeleteDialogComponent,
        LichSuThaoTacHopDongPopupComponent,
        LichSuThaoTacHopDongDeletePopupComponent,
    ],
    entryComponents: [
        LichSuThaoTacHopDongComponent,
        LichSuThaoTacHopDongDialogComponent,
        LichSuThaoTacHopDongPopupComponent,
        LichSuThaoTacHopDongDeleteDialogComponent,
        LichSuThaoTacHopDongDeletePopupComponent,
    ],
    providers: [
        LichSuThaoTacHopDongService,
        LichSuThaoTacHopDongPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipLichSuThaoTacHopDongModule {}
