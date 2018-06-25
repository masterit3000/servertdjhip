import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TaiSanComponent } from './tai-san.component';
import { TaiSanDetailComponent } from './tai-san-detail.component';
import { TaiSanPopupComponent } from './tai-san-dialog.component';
import { TaiSanDeletePopupComponent } from './tai-san-delete-dialog.component';

export const taiSanRoute: Routes = [
    {
        path: 'tai-san',
        component: TaiSanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.taiSan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tai-san/:id',
        component: TaiSanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.taiSan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taiSanPopupRoute: Routes = [
    {
        path: 'tai-san-new',
        component: TaiSanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.taiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tai-san/:id/edit',
        component: TaiSanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.taiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tai-san/:id/delete',
        component: TaiSanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.taiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
