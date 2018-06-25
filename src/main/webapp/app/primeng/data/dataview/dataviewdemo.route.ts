import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DataViewDemoComponent } from './dataviewdemo.component';

export const dataviewDemoRoute: Route = {
    path: 'dataview',
    component: DataViewDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.dataview.title'
    },
    canActivate: [UserRouteAccessService]
};
