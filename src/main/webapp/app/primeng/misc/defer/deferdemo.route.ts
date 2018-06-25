import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DeferDemoComponent } from './deferdemo.component';

export const deferDemoRoute: Route = {
    path: 'defer',
    component: DeferDemoComponent,
    data: {
         
        pageTitle: 'primeng.misc.defer.title'
    },
    canActivate: [UserRouteAccessService]
};
