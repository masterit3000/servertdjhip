import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServertdjhipSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {SliderModule} from 'primeng/components/slider/slider';
import {InputTextModule} from 'primeng/components/inputtext/inputtext';
import {GrowlModule} from 'primeng/components/growl/growl';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    SliderDemoComponent,
    sliderDemoRoute
} from './';

const primeng_STATES = [
    sliderDemoRoute
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        FormsModule,
        SliderModule,
        GrowlModule,
        InputTextModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        SliderDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipSliderDemoModule {}
