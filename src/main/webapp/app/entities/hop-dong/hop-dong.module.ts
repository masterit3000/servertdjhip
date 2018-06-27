import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AutoCompleteModule} from 'primeng/components/autocomplete/autocomplete';
import {SelectButtonModule} from 'primeng/components/selectbutton/selectbutton';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';
import { ServertdjhipSharedModule } from '../../shared';
import {
    HopDongService,
    HopDongPopupService,
    HopDongComponent,
    HopDongDetailComponent,
    HopDongDialogComponent,
    HopDongPopupComponent,
    HopDongDeletePopupComponent,
    HopDongDeleteDialogComponent,
    hopDongRoute,
    hopDongPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopDongRoute,
    ...hopDongPopupRoute,
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        AutoCompleteModule,
        SelectButtonModule,
        WizardModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopDongComponent,
        HopDongDetailComponent,
        HopDongDialogComponent,
        HopDongDeleteDialogComponent,
        HopDongPopupComponent,
        HopDongDeletePopupComponent,
    ],
    entryComponents: [
        HopDongComponent,
        HopDongDialogComponent,
        HopDongPopupComponent,
        HopDongDeleteDialogComponent,
        HopDongDeletePopupComponent,
    ],
    providers: [
        HopDongService,
        HopDongPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipHopDongModule {}
