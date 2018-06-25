import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NhatKyComponent } from './nhat-ky.component';
import { NhatKyDetailComponent } from './nhat-ky-detail.component';
import { NhatKyPopupComponent } from './nhat-ky-dialog.component';
import { NhatKyDeletePopupComponent } from './nhat-ky-delete-dialog.component';

export const nhatKyRoute: Routes = [
    {
        path: 'nhat-ky',
        component: NhatKyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhatKy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nhat-ky/:id',
        component: NhatKyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhatKy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nhatKyPopupRoute: Routes = [
    {
        path: 'nhat-ky-new',
        component: NhatKyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhatKy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhat-ky/:id/edit',
        component: NhatKyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhatKy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhat-ky/:id/delete',
        component: NhatKyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhatKy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
