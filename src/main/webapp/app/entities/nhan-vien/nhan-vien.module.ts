import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AutoCompleteModule } from 'primeng/components/autocomplete/autocomplete';
import { SelectButtonModule } from 'primeng/components/selectbutton/selectbutton';
import { CalendarModule } from 'primeng/calendar';
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
    nhanVienPopupRoute
} from './';
import { PhanQuyenNhanVienComponent } from './phan-quyen-nhan-vien/phan-quyen-nhan-vien.component';

const ENTITY_STATES = [...nhanVienRoute, ...nhanVienPopupRoute];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        ServertdjhipAdminModule,
        AutoCompleteModule,
        SelectButtonModule,
        CalendarModule,

        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NhanVienComponent,
        NhanVienDetailComponent,
        NhanVienDialogComponent,
        NhanVienDeleteDialogComponent,
        NhanVienPopupComponent,
        NhanVienDeletePopupComponent,
        PhanQuyenNhanVienComponent
    ],
    entryComponents: [
        NhanVienComponent,
        NhanVienDialogComponent,
        NhanVienPopupComponent,
        NhanVienDeleteDialogComponent,
        NhanVienDeletePopupComponent
    ],
    providers: [NhanVienService, NhanVienPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipNhanVienModule {}
