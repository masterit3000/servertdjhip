import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { TabMenuDemoComponent } from './tabmenudemo.component';

export const tabmenuDemoRoute: Route = {
    path: 'tabmenu',
    component: TabMenuDemoComponent,
    data: {
         
        pageTitle: 'primeng.menu.tabmenu.title'
    },
    canActivate: [UserRouteAccessService]
};
