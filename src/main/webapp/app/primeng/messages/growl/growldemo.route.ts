import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { GrowlDemoComponent } from './growldemo.component';

export const growlDemoRoute: Route = {
    path: 'growl',
    component: GrowlDemoComponent,
    data: {
         
        pageTitle: 'primeng.messages.growl.title'
    },
    canActivate: [UserRouteAccessService]
};
