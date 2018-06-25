import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { BlockUIDemoComponent } from './blockuidemo.component';

export const blockuiDemoRoute: Route = {
    path: 'blockui',
    component: BlockUIDemoComponent,
    data: {
         
        pageTitle: 'primeng.misc.blockui.title'
    },
    canActivate: [UserRouteAccessService]
};
