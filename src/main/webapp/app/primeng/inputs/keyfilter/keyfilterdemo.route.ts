import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { KeyFilterDemoComponent } from './keyfilterdemo.component';

export const keyFilterDemoRoute: Route = {
    path: 'keyfilter',
    component: KeyFilterDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.keyfilter.title'
    },
    canActivate: [UserRouteAccessService]
};
