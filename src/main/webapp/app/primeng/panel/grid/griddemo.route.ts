import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { GridDemoComponent } from './griddemo.component';

export const gridDemoRoute: Route = {
    path: 'grid',
    component: GridDemoComponent,
    data: {
         
        pageTitle: 'primeng.panel.grid.title'
    },
    canActivate: [UserRouteAccessService]
};
