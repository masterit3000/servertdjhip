import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { PaginatorDemoComponent } from './paginatordemo.component';

export const paginatorDemoRoute: Route = {
    path: 'paginator',
    component: PaginatorDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.paginator.title'
    },
    canActivate: [UserRouteAccessService]
};
