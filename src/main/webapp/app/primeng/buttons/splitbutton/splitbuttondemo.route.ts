import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { SplitbuttonDemoComponent } from './splitbuttondemo.component';

export const splitbuttonDemoRoute: Route = {
    path: 'splitbutton',
    component: SplitbuttonDemoComponent,
    data: {
         
        pageTitle: 'primeng.buttons.splitbutton.title'
    },
    canActivate: [UserRouteAccessService]
};
