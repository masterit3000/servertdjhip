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
    thuChiPopupRoute,
} from './';


const ENTITY_STATES = [
    ...thuChiRoute,
    ...thuChiPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ThuChiComponent,
        ThuChiDetailComponent,
        ThuChiDialogComponent,
        ThuChiDeleteDialogComponent,
        ThuChiPopupComponent,
        ThuChiDeletePopupComponent,
      
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
