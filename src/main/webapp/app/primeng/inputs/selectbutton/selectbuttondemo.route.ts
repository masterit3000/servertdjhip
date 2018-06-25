import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { SelectButtonDemoComponent } from './selectbuttondemo.component';

export const selectbuttonDemoRoute: Route = {
    path: 'selectbutton',
    component: SelectButtonDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.selectbutton.title'
    },
    canActivate: [UserRouteAccessService]
};
