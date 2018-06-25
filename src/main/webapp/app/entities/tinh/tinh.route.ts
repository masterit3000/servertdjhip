import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TinhComponent } from './tinh.component';
import { TinhDetailComponent } from './tinh-detail.component';
import { TinhPopupComponent } from './tinh-dialog.component';
import { TinhDeletePopupComponent } from './tinh-delete-dialog.component';

export const tinhRoute: Routes = [
    {
        path: 'tinh',
        component: TinhComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.tinh.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tinh/:id',
        component: TinhDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.tinh.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tinhPopupRoute: Routes = [
    {
        path: 'tinh-new',
        component: TinhPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.tinh.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tinh/:id/edit',
        component: TinhPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.tinh.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tinh/:id/delete',
        component: TinhDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.tinh.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
