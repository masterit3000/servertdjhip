import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
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
import {TabViewModule} from 'primeng/tabview';
import {InputTextModule} from 'primeng/inputtext';
import { BatHoMoiComponent } from './bat-ho-chuc-nang/bat-ho-moi/bat-ho-moi.component';

const ENTITY_STATES = [
    ...batHoRoute,
    ...batHoPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        TabViewModule,
        InputTextModule
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
        BatHoMoiComponent
    ],
    entryComponents: [
        BatHoComponent,
        BatHoDialogComponent,
        BatHoPopupComponent,
        BatHoDeleteDialogComponent,
        BatHoDeletePopupComponent
    ],
    providers: [
        BatHoService,
        BatHoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipBatHoModule {}
