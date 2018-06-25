import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { XaComponent } from './xa.component';
import { XaDetailComponent } from './xa-detail.component';
import { XaPopupComponent } from './xa-dialog.component';
import { XaDeletePopupComponent } from './xa-delete-dialog.component';

export const xaRoute: Routes = [
    {
        path: 'xa',
        component: XaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.xa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'xa/:id',
        component: XaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.xa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const xaPopupRoute: Routes = [
    {
        path: 'xa-new',
        component: XaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.xa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'xa/:id/edit',
        component: XaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.xa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'xa/:id/delete',
        component: XaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.xa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
