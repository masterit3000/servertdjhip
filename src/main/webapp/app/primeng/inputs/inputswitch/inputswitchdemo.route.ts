import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { InputSwitchDemoComponent } from './inputswitchdemo.component';

export const inputswitchDemoRoute: Route = {
    path: 'inputswitch',
    component: InputSwitchDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.inputswitch.title'
    },
    canActivate: [UserRouteAccessService]
};
