import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { RadarchartDemoComponent } from '../../charts/radarchart/radarchartdemo.component';

export const radarchartDemoRoute: Route = {
    path: 'radarchart',
    component: RadarchartDemoComponent,
    data: {
         
        pageTitle: 'primeng.charts.radarchart.title'
    },
    canActivate: [UserRouteAccessService]
};
