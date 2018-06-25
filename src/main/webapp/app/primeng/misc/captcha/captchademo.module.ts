import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ServertdjhipSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/components/growl/growl';
import {CaptchaModule} from 'primeng/components/captcha/captcha';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    CaptchaDemoComponent,
    captchaDemoRoute
} from './';

const primeng_STATES = [
    captchaDemoRoute
];

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        GrowlModule,
        CaptchaModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        CaptchaDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipCaptchaDemoModule {}
