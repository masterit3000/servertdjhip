import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HuyenComponent } from './huyen.component';
import { HuyenDetailComponent } from './huyen-detail.component';
import { HuyenPopupComponent } from './huyen-dialog.component';
import { HuyenDeletePopupComponent } from './huyen-delete-dialog.component';

export const huyenRoute: Routes = [
    {
        path: 'huyen',
        component: HuyenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.huyen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'huyen/:id',
        component: HuyenDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.huyen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const huyenPopupRoute: Routes = [
    {
        path: 'huyen-new',
        component: HuyenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.huyen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'huyen/:id/edit',
        component: HuyenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.huyen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'huyen/:id/delete',
        component: HuyenDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.huyen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
