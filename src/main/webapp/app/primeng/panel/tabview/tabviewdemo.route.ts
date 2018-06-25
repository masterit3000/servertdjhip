import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { TabViewDemoComponent } from './tabviewdemo.component';

export const tabviewDemoRoute: Route = {
    path: 'tabview',
    component: TabViewDemoComponent,
    data: {
         
        pageTitle: 'primeng.panel.tabview.title'
    },
    canActivate: [UserRouteAccessService]
};
