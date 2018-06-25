import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DataListDemoComponent } from './datalistdemo.component';

export const datalistDemoRoute: Route = {
    path: 'datalist',
    component: DataListDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.datalist.title'
    },
    canActivate: [UserRouteAccessService]
};
