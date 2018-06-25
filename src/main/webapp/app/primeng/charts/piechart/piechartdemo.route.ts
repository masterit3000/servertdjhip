import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { PiechartDemoComponent } from '../../charts/piechart/piechartdemo.component';

export const piechartDemoRoute: Route = {
    path: 'piechart',
    component: PiechartDemoComponent,
    data: {
         
        pageTitle: 'primeng.charts.piechart.title'
    },
    canActivate: [UserRouteAccessService]
};
