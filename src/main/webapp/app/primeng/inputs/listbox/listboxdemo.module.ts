import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {ListboxModule} from 'primeng/components/listbox/listbox';
import {SelectButtonModule} from 'primeng/components/selectbutton/selectbutton';
import {GrowlModule} from 'primeng/components/growl/growl';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    ListboxDemoComponent,
    listboxDemoRoute
} from './';

const primeng_STATES = [
    listboxDemoRoute
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        FormsModule,
        ListboxModule,
        GrowlModule,
        SelectButtonModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ListboxDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipListboxDemoModule {}
