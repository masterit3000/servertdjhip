import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ServertdjhipSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {SlideMenuModule} from 'primeng/components/slidemenu/slidemenu';
import {ButtonModule} from 'primeng/components/button/button';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    SlideMenuDemoComponent,
    slidemenuDemoRoute
} from './';

const primeng_STATES = [
    slidemenuDemoRoute
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        SlideMenuModule,
        GrowlModule,
        ButtonModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        SlideMenuDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipSlideMenuDemoModule {}
