import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { InputGroupDemoComponent } from './inputgroupdemo.component';

export const inputGroupDemoRoute: Route = {
    path: 'inputgroup',
    component: InputGroupDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.inputgroup.title'
    },
    canActivate: [UserRouteAccessService]
};
