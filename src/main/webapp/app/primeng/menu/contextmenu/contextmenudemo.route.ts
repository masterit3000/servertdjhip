import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { ContextMenuDemoComponent } from './contextmenudemo.component';

export const contextmenuDemoRoute: Route = {
    path: 'contextmenu',
    component: ContextMenuDemoComponent,
    data: {
         
        pageTitle: 'primeng.menu.contextmenu.title'
    },
    canActivate: [UserRouteAccessService]
};
