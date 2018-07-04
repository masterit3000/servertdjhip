import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../shared';
import {
    ThuChiService,
    ThuChiPopupService,
    ThuChiComponent,
    ThuChiDetailComponent,
    ThuChiDialogComponent,
    ThuChiPopupComponent,
    ThuChiDeletePopupComponent,
    ThuChiDeleteDialogComponent,
    thuChiRoute,
    thuChiPopupRoute
} from './';
import { ThuHoatDongComponent } from './thu-hoat-dong/thu-hoat-dong.component';
import { ChiHoatDongComponent } from './chi-hoat-dong/chi-hoat-dong.component';

const ENTITY_STATES = [...thuChiRoute, ...thuChiPopupRoute];

@NgModule({
    imports: [ServertdjhipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ThuChiComponent,
        ThuChiDetailComponent,
        ThuChiDialogComponent,
        ThuChiDeleteDialogComponent,
        ThuChiPopupComponent,
        ThuChiDeletePopupComponent,
        ThuHoatDongComponent,
        ChiHoatDongComponent
    ],
    entryComponents: [
        ThuChiComponent,
        ThuChiDialogComponent,
        ThuChiPopupComponent,
        ThuChiDeleteDialogComponent,
        ThuChiDeletePopupComponent
    ],
    providers: [ThuChiService, ThuChiPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipThuChiModule {}
