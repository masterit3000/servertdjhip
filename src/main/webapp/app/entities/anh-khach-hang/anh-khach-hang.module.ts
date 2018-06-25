import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    AnhKhachHangService,
    AnhKhachHangPopupService,
    AnhKhachHangComponent,
    AnhKhachHangDetailComponent,
    AnhKhachHangDialogComponent,
    AnhKhachHangPopupComponent,
    AnhKhachHangDeletePopupComponent,
    AnhKhachHangDeleteDialogComponent,
    anhKhachHangRoute,
    anhKhachHangPopupRoute,
} from './';

const ENTITY_STATES = [
    ...anhKhachHangRoute,
    ...anhKhachHangPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnhKhachHangComponent,
        AnhKhachHangDetailComponent,
        AnhKhachHangDialogComponent,
        AnhKhachHangDeleteDialogComponent,
        AnhKhachHangPopupComponent,
        AnhKhachHangDeletePopupComponent,
    ],
    entryComponents: [
        AnhKhachHangComponent,
        AnhKhachHangDialogComponent,
        AnhKhachHangPopupComponent,
        AnhKhachHangDeleteDialogComponent,
        AnhKhachHangDeletePopupComponent,
    ],
    providers: [
        AnhKhachHangService,
        AnhKhachHangPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipAnhKhachHangModule {}
