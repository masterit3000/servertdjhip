import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
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
import {TabViewModule} from 'primeng/tabview';
import { VayLaiDongLaiComponent } from './vay-lai-chuc-nang/vay-lai-dong-lai/vay-lai-dong-lai.component';
import { VayLaiTraBotGocComponent } from './vay-lai-chuc-nang/vay-lai-tra-bot-goc/vay-lai-tra-bot-goc.component';
import { VayLaiVayThemComponent } from './vay-lai-chuc-nang/vay-lai-vay-them/vay-lai-vay-them.component';
import { VayLaiGiaHanComponent } from './vay-lai-chuc-nang/vay-lai-gia-han/vay-lai-gia-han.component';
import { VayLaiDongHopDongComponent } from './vay-lai-chuc-nang/vay-lai-dong-hop-dong/vay-lai-dong-hop-dong.component';
import { VayLaiNoComponent } from './vay-lai-chuc-nang/vay-lai-no/vay-lai-no.component';
import { VayLaiLichSuComponent } from './vay-lai-chuc-nang/vay-lai-lich-su/vay-lai-lich-su.component';
import { VayLaiLichSuTraChamComponent } from './vay-lai-chuc-nang/vay-lai-lich-su-tra-cham/vay-lai-lich-su-tra-cham.component';
import {InputTextModule} from 'primeng/inputtext';
import {CalendarModule} from 'primeng/calendar';


const ENTITY_STATES = [
    ...vayLaiRoute,
    ...vayLaiPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        TabViewModule,
        InputTextModule,
        CalendarModule
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
        VayLaiLichSuTraChamComponent
        
    ],

    entryComponents: [
        VayLaiComponent,
        VayLaiDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeleteDialogComponent,
        VayLaiDeletePopupComponent,
    ],
    providers: [
        VayLaiService,
        VayLaiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipVayLaiModule { }
