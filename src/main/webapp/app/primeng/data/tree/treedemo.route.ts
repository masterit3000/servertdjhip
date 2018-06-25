import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { TreeDemoComponent } from './treedemo.component';

export const treeDemoRoute: Route = {
    path: 'tree',
    component: TreeDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.tree.title'
    },
    canActivate: [UserRouteAccessService]
};
