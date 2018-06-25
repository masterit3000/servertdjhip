import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    KhachHangService,
    KhachHangPopupService,
    KhachHangComponent,
    KhachHangDetailComponent,
    KhachHangDialogComponent,
    KhachHangPopupComponent,
    KhachHangDeletePopupComponent,
    KhachHangDeleteDialogComponent,
    khachHangRoute,
    khachHangPopupRoute,
} from './';

const ENTITY_STATES = [
    ...khachHangRoute,
    ...khachHangPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KhachHangComponent,
        KhachHangDetailComponent,
        KhachHangDialogComponent,
        KhachHangDeleteDialogComponent,
        KhachHangPopupComponent,
        KhachHangDeletePopupComponent,
    ],
    entryComponents: [
        KhachHangComponent,
        KhachHangDialogComponent,
        KhachHangPopupComponent,
        KhachHangDeleteDialogComponent,
        KhachHangDeletePopupComponent,
    ],
    providers: [
        KhachHangService,
        KhachHangPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipKhachHangModule {}
