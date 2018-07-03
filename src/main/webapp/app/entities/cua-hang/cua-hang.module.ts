import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AutoCompleteModule} from 'primeng/components/autocomplete/autocomplete';
import {SelectButtonModule} from 'primeng/components/selectbutton/selectbutton';
import { ServertdjhipSharedModule } from '../../shared';
import{CalendarModule} from 'primeng/calendar';
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
import { TongQuatChuoiCuaHangComponent } from './tong-quat-chuoi-cua-hang/tong-quat-chuoi-cua-hang.component';
import { ThongTinChiTietCuaHangComponent } from './thong-tin-chi-tiet-cua-hang/thong-tin-chi-tiet-cua-hang.component';
import { CauHinhHangHoaComponent } from './cau-hinh-hang-hoa/cau-hinh-hang-hoa.component';
import { NhapTienQuyDauNgayComponent } from './nhap-tien-quy-dau-ngay/nhap-tien-quy-dau-ngay.component';

const ENTITY_STATES = [
    ...cuaHangRoute,
    ...cuaHangPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        AutoCompleteModule,
        SelectButtonModule,
        CalendarModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CuaHangComponent,
        CuaHangDetailComponent,
        CuaHangDialogComponent,
        CuaHangDeleteDialogComponent,
        CuaHangPopupComponent,
        CuaHangDeletePopupComponent,
        TongQuatChuoiCuaHangComponent,
        ThongTinChiTietCuaHangComponent,
        CauHinhHangHoaComponent,
        NhapTienQuyDauNgayComponent,
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
