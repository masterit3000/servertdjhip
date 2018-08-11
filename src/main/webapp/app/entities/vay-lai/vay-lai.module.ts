import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {TableModule} from 'primeng/table';
import { ServertdjhipSharedModule } from '../../shared';
import {DialogModule} from 'primeng/dialog';
import {GrowlModule} from 'primeng/growl';
import {GalleriaModule} from 'primeng/galleria';
import {
    VayLaiService,
    VayLaiPopupService,
    VayLaiComponent,
    VayLaiDetailComponent,
    VayLaiDialogComponent,
    VayLaiPopupComponent,
    VayLaiDeletePopupComponent,
    VayLaiDeleteDialogComponent,
    vayLaiRoute,
    vayLaiPopupRoute
} from './';
import { VayLaiChucNangComponent } from './vay-lai-chuc-nang/vay-lai-chuc-nang.component';
import { TabViewModule } from 'primeng/tabview';
import { VayLaiDongLaiComponent } from './vay-lai-chuc-nang/vay-lai-dong-lai/vay-lai-dong-lai.component';
import { VayLaiTraBotGocComponent } from './vay-lai-chuc-nang/vay-lai-tra-bot-goc/vay-lai-tra-bot-goc.component';
import { VayLaiVayThemComponent } from './vay-lai-chuc-nang/vay-lai-vay-them/vay-lai-vay-them.component';
import { VayLaiGiaHanComponent } from './vay-lai-chuc-nang/vay-lai-gia-han/vay-lai-gia-han.component';
import { VayLaiDongHopDongComponent } from './vay-lai-chuc-nang/vay-lai-dong-hop-dong/vay-lai-dong-hop-dong.component';
import { VayLaiNoComponent } from './vay-lai-chuc-nang/vay-lai-no/vay-lai-no.component';
import { VayLaiLichSuComponent } from './vay-lai-chuc-nang/vay-lai-lich-su/vay-lai-lich-su.component';
import { VayLaiLichSuTraChamComponent } from './vay-lai-chuc-nang/vay-lai-lich-su-tra-cham/vay-lai-lich-su-tra-cham.component';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { VayLaiChungTuComponent } from './vay-lai-chuc-nang/vay-lai-chung-tu/vay-lai-chung-tu.component';
import { FileUploadModule } from 'primeng/fileupload';
import { VayLaiMoiComponent } from './vay-lai-chuc-nang/vay-lai-moi/vay-lai-moi.component';
import { VayLaiKhachHangTableComponent } from './vay-lai-chuc-nang/vay-lai-khach-hang-table/vay-lai-khach-hang-table.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { KeyFilterModule } from 'primeng/keyfilter';
import {TooltipModule} from 'primeng/tooltip';
import {SpinnerModule} from 'primeng/spinner';

const ENTITY_STATES = [...vayLaiRoute, ...vayLaiPopupRoute];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        TabViewModule,
        InputTextModule,
        CalendarModule,
        InputTextareaModule,
        FileUploadModule,
        InputSwitchModule,
        AutoCompleteModule,
        ScrollPanelModule,
        KeyFilterModule,
        TooltipModule,
        TableModule,
        GrowlModule,
        SpinnerModule,
        GalleriaModule,
        DialogModule
    ],
    declarations: [
        VayLaiComponent,
        VayLaiDetailComponent,
        VayLaiDialogComponent,
        VayLaiDeleteDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeletePopupComponent,
        VayLaiChucNangComponent,
        VayLaiDongLaiComponent,
        VayLaiTraBotGocComponent,
        VayLaiVayThemComponent,
        VayLaiGiaHanComponent,
        VayLaiDongHopDongComponent,
        VayLaiNoComponent,
        VayLaiLichSuComponent,
        VayLaiLichSuTraChamComponent,
        VayLaiChungTuComponent,
        VayLaiMoiComponent,
        VayLaiKhachHangTableComponent
    ],

    entryComponents: [
        VayLaiComponent,
        VayLaiDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeleteDialogComponent,
        VayLaiDeletePopupComponent
    ],
    providers: [VayLaiService, VayLaiPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipVayLaiModule {}
