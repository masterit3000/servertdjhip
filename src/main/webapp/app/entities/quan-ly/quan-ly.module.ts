import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuanLyRoutingModule } from './quan-ly-routing.module';
import { BatHoAdminComponent } from './bat-ho/bat-ho.component';
import { BatHoDetailAdminComponent } from './bat-ho/bat-ho-detail/bat-ho-detail.component';
import { VayLaiAdminComponent } from './vay-lai/vay-lai.component';
import { VayLaiDetailAdminComponent } from './vay-lai/vay-lai-detail/vay-lai-detail.component';
import { KhachHangAdminComponent } from './khach-hang/khach-hang.component';
import { KhachHangDetailAdminComponent } from './khach-hang/khach-hang-detail/khach-hang-detail.component';
import { CuaHangAdminComponent } from './cua-hang/cua-hang.component';
import { CuaHangDetailAdminComponent } from './cua-hang/cua-hang-detail/cua-hang-detail.component';
import { NhanVienAdminComponent } from './nhan-vien/nhan-vien.component';
import { NhanVienDetailAdminComponent } from './nhan-vien/nhan-vien-detail/nhan-vien-detail.component';
import { TableModule } from 'primeng/table';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { FieldsetModule } from 'primeng/fieldset';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { KeyFilterModule } from 'primeng/keyfilter';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { TooltipModule } from 'primeng/tooltip';
import { SpinnerModule } from 'primeng/spinner';
import { CheckboxModule } from 'primeng/checkbox';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { FileUploadModule } from 'primeng/fileupload';
import { TabViewModule } from 'primeng/tabview';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import {GrowlModule} from 'primeng/growl';
import {DialogModule} from 'primeng/dialog';
import {GalleriaModule} from 'primeng/galleria';

@NgModule({
    imports: [
        CommonModule,
        QuanLyRoutingModule,
        TabViewModule,
        InputTextModule,
        InputTextareaModule,
        FileUploadModule,
        AutoCompleteModule,
        FieldsetModule,
        ScrollPanelModule,
        KeyFilterModule,
        CalendarModule,
        FormsModule,
        GalleriaModule,
        TooltipModule,
        TableModule,
        CheckboxModule,
        GrowlModule,
        SpinnerModule,
        ConfirmDialogModule,
        DialogModule
    ],
    declarations: [
        BatHoAdminComponent,
        BatHoDetailAdminComponent,
        VayLaiAdminComponent,
        VayLaiDetailAdminComponent,
        KhachHangAdminComponent,
        KhachHangDetailAdminComponent,
        CuaHangAdminComponent,
        CuaHangDetailAdminComponent,
        NhanVienAdminComponent,
        NhanVienDetailAdminComponent
    ]
})
export class QuanLyModule {}
