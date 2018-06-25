import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnhTaiSanComponent } from './anh-tai-san.component';
import { AnhTaiSanDetailComponent } from './anh-tai-san-detail.component';
import { AnhTaiSanPopupComponent } from './anh-tai-san-dialog.component';
import { AnhTaiSanDeletePopupComponent } from './anh-tai-san-delete-dialog.component';

export const anhTaiSanRoute: Routes = [
    {
        path: 'anh-tai-san',
        component: AnhTaiSanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.anhTaiSan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anh-tai-san/:id',
        component: AnhTaiSanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.anhTaiSan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anhTaiSanPopupRoute: Routes = [
    {
        path: 'anh-tai-san-new',
        component: AnhTaiSanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.anhTaiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anh-tai-san/:id/edit',
        component: AnhTaiSanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.anhTaiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anh-tai-san/:id/delete',
        component: AnhTaiSanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.anhTaiSan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
