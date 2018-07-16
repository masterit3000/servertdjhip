import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {CalendarModule} from 'primeng/calendar';
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
    thuChiPopupRoute,
} from './';
import { ThuHoatDongComponent } from './thu-hoat-dong/thu-hoat-dong.component';
import { ChiHoatDongComponent } from './chi-hoat-dong/chi-hoat-dong.component';
import {SpinnerModule} from 'primeng/spinner';
import { GopVonComponent } from './gop-von/gop-von.component';
import { RutVonComponent } from './rut-von/rut-von.component';


const ENTITY_STATES = [
    ...thuChiRoute,
    ...thuChiPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        CalendarModule,
        SpinnerModule
    ],
    declarations: [
        ThuChiComponent,
        ThuChiDetailComponent,
        ThuChiDialogComponent,
        ThuChiDeleteDialogComponent,
        ThuChiPopupComponent,
        ThuChiDeletePopupComponent,
        ThuHoatDongComponent,
        ChiHoatDongComponent,
        GopVonComponent,
        RutVonComponent,
     
    ],
    entryComponents: [
        ThuChiComponent,
        ThuChiDialogComponent,
        ThuChiPopupComponent,
        ThuChiDeleteDialogComponent,
        ThuChiDeletePopupComponent,
    ],
    providers: [
        ThuChiService,
        ThuChiPopupService,
       
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipThuChiModule {}
