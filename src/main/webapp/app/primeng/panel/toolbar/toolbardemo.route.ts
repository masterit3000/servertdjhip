import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { ToolbarDemoComponent } from './toolbardemo.component';

export const toolbarDemoRoute: Route = {
    path: 'toolbar',
    component: ToolbarDemoComponent,
    data: {
         
        pageTitle: 'primeng.panel.toolbar.title'
    },
    canActivate: [UserRouteAccessService]
};
