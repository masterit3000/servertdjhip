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
import { BhDongHdComponent } from './bat-ho-chuc-nang/bh-dong-hd/bh-dong-hd.component';
import { BhLichDongTienComponent } from './bat-ho-chuc-nang/bh-lich-dong-tien/bh-lich-dong-tien.component';
import { BhNoComponent } from './bat-ho-chuc-nang/bh-no/bh-no.component';


const ENTITY_STATES = [
    ...batHoRoute,
    ...batHoPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BatHoComponent,
        BatHoDetailComponent,
        BatHoDialogComponent,
        BatHoDeleteDialogComponent,
        BatHoPopupComponent,
        BatHoDeletePopupComponent,
        BatHoChucNangComponent,
        BhDongHdComponent,
        BhLichDongTienComponent,
        BhNoComponent
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
