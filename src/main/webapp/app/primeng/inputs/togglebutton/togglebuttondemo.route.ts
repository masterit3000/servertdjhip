import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { ToggleButtonDemoComponent } from './togglebuttondemo.component';

export const spinnerDemoRoute: Route = {
    path: 'togglebutton',
    component: ToggleButtonDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.togglebutton.title'
    },
    canActivate: [UserRouteAccessService]
};
