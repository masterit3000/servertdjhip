;
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ServertdjhipSharedModule } from '../shared';
import { TableModule } from 'primeng/table';
import {GrowlModule} from 'primeng/growl';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [
        ServertdjhipSharedModule,
        TableModule,
        TableModule,
        ServertdjhipSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipHomeModule {}
