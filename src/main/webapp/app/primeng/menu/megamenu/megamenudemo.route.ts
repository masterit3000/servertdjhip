import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { MegaMenuDemoComponent } from './megamenudemo.component';

export const megamenuDemoRoute: Route = {
    path: 'megamenu',
    component: MegaMenuDemoComponent,
    data: {
         
        pageTitle: 'primeng.menu.megamenu.title'
    },
    canActivate: [UserRouteAccessService]
};
