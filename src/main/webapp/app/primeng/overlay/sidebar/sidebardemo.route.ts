import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { SideBarDemoComponent } from './sidebardemo.component';

export const sidebarDemoRoute: Route = {
    path: 'sidebar',
    component: SideBarDemoComponent,
    data: {
         
        pageTitle: 'primeng.overlay.sidebar.title'
    },
    canActivate: [UserRouteAccessService]
};
