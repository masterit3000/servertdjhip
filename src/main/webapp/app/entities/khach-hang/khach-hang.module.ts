import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AutoCompleteModule } from 'primeng/components/autocomplete/autocomplete';
import { SelectButtonModule } from 'primeng/components/selectbutton/selectbutton';
//
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
    khachHangPopupRoute
} from './';
import { TableModule } from 'primeng/table';
import { CheckThongTinKhachHangComponent } from './check-thong-tin-khach-hang/check-thong-tin-khach-hang.component';
import { KhachCanVayComponent } from './khach-can-vay/khach-can-vay.component';
import { TooltipModule } from 'primeng/tooltip';
import { CalendarModule } from 'primeng/calendar';
import {
    BrowserAnimationsModule,
    NoopAnimationsModule
} from '@angular/platform-browser/animations';
import { KhachHangChucNangComponent } from './khach-hang-chuc-nang/khach-hang-chuc-nang.component';
// 2 module nay phai cho vao de co hieu ugn day du
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { KeyFilterModule } from 'primeng/keyfilter';
import { FormsModule } from '@angular/forms';

const ENTITY_STATES = [...khachHangRoute, ...khachHangPopupRoute];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        SelectButtonModule,
        AutoCompleteModule,
        RouterModule.forChild(ENTITY_STATES),
        CalendarModule,
        InputTextModule,
        InputTextareaModule,
        BrowserAnimationsModule,
        NoopAnimationsModule,
        TooltipModule,
        TableModule,
        InputTextModule,
        InputTextareaModule,
        KeyFilterModule,
        FormsModule
    ],
    declarations: [
        KhachHangComponent,
        KhachHangDetailComponent,
        KhachHangDialogComponent,
        KhachHangDeleteDialogComponent,
        KhachHangPopupComponent,
        KhachHangDeletePopupComponent,
        CheckThongTinKhachHangComponent,
        KhachCanVayComponent,
        KhachHangChucNangComponent,
    ],
    entryComponents: [
        KhachHangComponent,
        KhachHangDialogComponent,
        KhachHangPopupComponent,
        KhachHangDeleteDialogComponent,
        KhachHangDeletePopupComponent
    ],
    providers: [KhachHangService, KhachHangPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipKhachHangModule {}
