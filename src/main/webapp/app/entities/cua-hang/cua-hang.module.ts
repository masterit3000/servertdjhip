import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    CuaHangService,
    CuaHangPopupService,
    CuaHangComponent,
    CuaHangDetailComponent,
    CuaHangDialogComponent,
    CuaHangPopupComponent,
    CuaHangDeletePopupComponent,
    CuaHangDeleteDialogComponent,
    cuaHangRoute,
    cuaHangPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cuaHangRoute,
    ...cuaHangPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CuaHangComponent,
        CuaHangDetailComponent,
        CuaHangDialogComponent,
        CuaHangDeleteDialogComponent,
        CuaHangPopupComponent,
        CuaHangDeletePopupComponent,
    ],
    entryComponents: [
        CuaHangComponent,
        CuaHangDialogComponent,
        CuaHangPopupComponent,
        CuaHangDeleteDialogComponent,
        CuaHangDeletePopupComponent,
    ],
    providers: [
        CuaHangService,
        CuaHangPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipCuaHangModule {}
