import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { SliderDemoComponent } from './sliderdemo.component';

export const sliderDemoRoute: Route = {
    path: 'slider',
    component: SliderDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.slider.title'
    },
    canActivate: [UserRouteAccessService]
};
