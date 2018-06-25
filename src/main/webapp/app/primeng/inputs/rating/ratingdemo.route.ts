import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { RatingDemoComponent } from './ratingdemo.component';

export const ratingDemoRoute: Route = {
    path: 'rating',
    component: RatingDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.rating.title'
    },
    canActivate: [UserRouteAccessService]
};
