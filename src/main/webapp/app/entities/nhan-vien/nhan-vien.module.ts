import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AutoCompleteModule} from 'primeng/components/autocomplete/autocomplete';
import {SelectButtonModule} from 'primeng/components/selectbutton/selectbutton';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';
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
        AutoCompleteModule,
        SelectButtonModule,
        WizardModule,
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
