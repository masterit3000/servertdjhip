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
import { VlDongHdComponent } from './vay-lai-chuc-nang/vl-dong-hd/vl-dong-hd.component';
import { VlDongLaiComponent } from './vay-lai-chuc-nang/vl-dong-lai/vl-dong-lai.component';
import { VlGiaHanComponent } from './vay-lai-chuc-nang/vl-gia-han/vl-gia-han.component';
import { VlLichSuComponent } from './vay-lai-chuc-nang/vl-lich-su/vl-lich-su.component';
import { VlLichSuTraChamComponent } from './vay-lai-chuc-nang/vl-lich-su-tra-cham/vl-lich-su-tra-cham.component';
import { VlNoComponent } from './vay-lai-chuc-nang/vl-no/vl-no.component';
import { VlTraBotGocComponent } from './vay-lai-chuc-nang/vl-tra-bot-goc/vl-tra-bot-goc.component';
import { VlVayThemComponent } from './vay-lai-chuc-nang/vl-vay-them/vl-vay-them.component';
import { VayLaiChucNangComponent } from './vay-lai-chuc-nang/vay-lai-chuc-nang.component';

const ENTITY_STATES = [
    ...vayLaiRoute,
    ...vayLaiPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VayLaiComponent,
        VayLaiDetailComponent,
        VayLaiDialogComponent,
        VayLaiDeleteDialogComponent,
        VayLaiPopupComponent,
        VayLaiDeletePopupComponent,
        VlDongHdComponent,
        VlDongLaiComponent,
        VlGiaHanComponent,
        VlLichSuComponent,
        VlLichSuTraChamComponent,
        VlNoComponent,
        VlTraBotGocComponent,
        VlVayThemComponent,
        VayLaiChucNangComponent
        
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
