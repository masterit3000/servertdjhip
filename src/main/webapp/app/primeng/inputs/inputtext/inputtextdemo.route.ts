import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { InputTextDemoComponent } from './inputtextdemo.component';

export const inputTextDemoRoute: Route = {
    path: 'inputtext',
    component: InputTextDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.inputtext.title'
    },
    canActivate: [UserRouteAccessService]
};
