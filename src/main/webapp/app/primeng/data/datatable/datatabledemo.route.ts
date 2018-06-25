import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DataTableDemoComponent } from './datatabledemo.component';

export const datatableDemoRoute: Route = {
    path: 'datatable',
    component: DataTableDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.datatable.title'
    },
    canActivate: [UserRouteAccessService]
};
