import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { DoughnutchartDemoComponent } from '../../charts/doughnutchart/doughnutchartdemo.component';

export const doughnutchartDemoRoute: Route = {
    path: 'doughnutchart',
    component: DoughnutchartDemoComponent,
    data: {
         
        pageTitle: 'primeng.charts.doughnutchart.title'
    },
    canActivate: [UserRouteAccessService]
};
