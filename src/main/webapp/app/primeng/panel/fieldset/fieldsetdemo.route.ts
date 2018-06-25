import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { FieldsetDemoComponent } from './fieldsetdemo.component';

export const fieldsetDemoRoute: Route = {
    path: 'fieldset',
    component: FieldsetDemoComponent,
    data: {
         
        pageTitle: 'primeng.panel.fieldset.title'
    },
    canActivate: [UserRouteAccessService]
};
