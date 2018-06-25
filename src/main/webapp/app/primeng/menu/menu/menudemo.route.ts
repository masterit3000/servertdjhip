import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { MenuDemoComponent } from './menudemo.component';

export const menuDemoRoute: Route = {
    path: 'menu',
    component: MenuDemoComponent,
    data: {
         
        pageTitle: 'primeng.menu.menu.title'
    },
    canActivate: [UserRouteAccessService]
};
