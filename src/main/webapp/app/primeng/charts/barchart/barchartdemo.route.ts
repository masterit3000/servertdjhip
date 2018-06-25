import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { BarchartDemoComponent } from '../../charts/barchart/barchartdemo.component';

export const barchartDemoRoute: Route = {
    path: 'barchart',
    component: BarchartDemoComponent,
    data: {
         
        pageTitle: 'primeng.charts.barchart.title'
    },
    canActivate: [UserRouteAccessService]
};
