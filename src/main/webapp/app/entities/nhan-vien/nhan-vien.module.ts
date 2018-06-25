import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import { ServertdjhipAdminModule } from '../../admin/admin.module';
import {
    NhanVienService,
    NhanVienPopupService,
    NhanVienComponent,
    NhanVienDetailComponent,
    NhanVienDialogComponent,
    NhanVienPopupComponent,
    NhanVienDeletePopupComponent,
    NhanVienDeleteDialogComponent,
    nhanVienRoute,
    nhanVienPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nhanVienRoute,
    ...nhanVienPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        ServertdjhipAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NhanVienComponent,
        NhanVienDetailComponent,
        NhanVienDialogComponent,
        NhanVienDeleteDialogComponent,
        NhanVienPopupComponent,
        NhanVienDeletePopupComponent,
    ],
    entryComponents: [
        NhanVienComponent,
        NhanVienDialogComponent,
        NhanVienPopupComponent,
        NhanVienDeleteDialogComponent,
        NhanVienDeletePopupComponent,
    ],
    providers: [
        NhanVienService,
        NhanVienPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipNhanVienModule {}
