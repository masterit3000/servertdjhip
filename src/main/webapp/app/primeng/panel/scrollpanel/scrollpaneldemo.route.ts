import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { ScrollPanelDemoComponent } from './scrollpaneldemo.component';

export const scrollPanelDemoRoute: Route = {
    path: 'scrollpanel',
    component: ScrollPanelDemoComponent,
    data: {
         
        pageTitle: 'primeng.panel.scrollpanel.title'
    },
    canActivate: [UserRouteAccessService]
};
