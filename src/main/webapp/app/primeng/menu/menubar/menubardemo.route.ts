import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { MenuBarDemoComponent } from './menubardemo.component';

export const menubarDemoRoute: Route = {
    path: 'menubar',
    component: MenuBarDemoComponent,
    data: {
         
        pageTitle: 'primeng.menu.menubar.title'
    },
    canActivate: [UserRouteAccessService]
};
