import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DataGridDemoComponent } from './datagriddemo.component';

export const datagridDemoRoute: Route = {
    path: 'datagrid',
    component: DataGridDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.datagrid.title'
    },
    canActivate: [UserRouteAccessService]
};
