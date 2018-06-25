import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { OrgChartDemoComponent } from './orgchartdemo.component';

export const orgchartDemoRoute: Route = {
    path: 'orgchart',
    component: OrgChartDemoComponent,
    data: {
         
        pageTitle: 'primeng.data.orgchart.title'
    },
    canActivate: [UserRouteAccessService]
};
