import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ServertdjhipSharedModule } from '../../shared';
import { TableModule } from 'primeng/table';
import {
    BatHoService,
    BatHoPopupService,
    BatHoComponent,
    BatHoDetailComponent,
    BatHoDialogComponent,
    BatHoPopupComponent,
    BatHoDeletePopupComponent,
    BatHoDeleteDialogComponent,
    batHoRoute,
    batHoPopupRoute
} from './';
import { BatHoChucNangComponent } from './bat-ho-chuc-nang/bat-ho-chuc-nang.component';
import { BatHoLichDongTienComponent } from './bat-ho-chuc-nang/bat-ho-lich-dong-tien/bat-ho-lich-dong-tien.component';
import { BatHoDongHopDongComponent } from './bat-ho-chuc-nang/bat-ho-dong-hop-dong/bat-ho-dong-hop-dong.component';
import { BatHoNoComponent } from './bat-ho-chuc-nang/bat-ho-no/bat-ho-no.component';
import { BatHoLichSuComponent } from './bat-ho-chuc-nang/bat-ho-lich-su/bat-ho-lich-su.component';
import { BatHoDaoHoComponent } from './bat-ho-chuc-nang/bat-ho-dao-ho/bat-ho-dao-ho.component';
import { TabViewModule } from 'primeng/tabview';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { BatHoChungTuComponent } from './bat-ho-chuc-nang/bat-ho-chung-tu/bat-ho-chung-tu.component';
import { FileUploadModule } from 'primeng/fileupload';
import { BatHoMoiComponent } from './bat-ho-chuc-nang/bat-ho-moi/bat-ho-moi.component';
import { BatHoKhachHangTableComponent } from './bat-ho-chuc-nang/bat-ho-khach-hang-table/bat-ho-khach-hang-table.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { FieldsetModule } from 'primeng/fieldset';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { KeyFilterModule } from 'primeng/keyfilter';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { TooltipModule } from 'primeng/tooltip';
import { SpinnerModule } from 'primeng/spinner';
import { CheckboxModule } from 'primeng/checkbox';

const ENTITY_STATES = [...batHoRoute, ...batHoPopupRoute];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES),
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
        TooltipModule,
        TableModule,
        SpinnerModule,
        CheckboxModule
    ],
    declarations: [
        BatHoComponent,
        BatHoDetailComponent,
        BatHoDialogComponent,
        BatHoDeleteDialogComponent,
        BatHoPopupComponent,
        BatHoDeletePopupComponent,
        BatHoChucNangComponent,
        BatHoLichDongTienComponent,
        BatHoDongHopDongComponent,
        BatHoNoComponent,
        BatHoLichSuComponent,
        BatHoDaoHoComponent,
        BatHoChungTuComponent,
        BatHoMoiComponent,
        BatHoKhachHangTableComponent
    ],
    entryComponents: [
        BatHoComponent,
        BatHoDialogComponent,
        BatHoPopupComponent,
        BatHoDeleteDialogComponent,
        BatHoDeletePopupComponent
    ],
    providers: [BatHoService, BatHoPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipBatHoModule {}
