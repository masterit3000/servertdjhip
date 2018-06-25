import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { TooltipDemoComponent } from './tooltipdemo.component';

export const tooltipDemoRoute: Route = {
    path: 'tooltip',
    component: TooltipDemoComponent,
    data: {
         
        pageTitle: 'primeng.overlay.tooltip.title'
    },
    canActivate: [UserRouteAccessService]
};
