import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { PasswordIndicatorDemoComponent } from './passwordindicatordemo.component';

export const passwordindicatorDemoRoute: Route = {
    path: 'passwordindicator',
    component: PasswordIndicatorDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.passwordindicator.title'
    },
    canActivate: [UserRouteAccessService]
};
