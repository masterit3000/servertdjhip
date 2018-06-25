import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CuaHangComponent } from './cua-hang.component';
import { CuaHangDetailComponent } from './cua-hang-detail.component';
import { CuaHangPopupComponent } from './cua-hang-dialog.component';
import { CuaHangDeletePopupComponent } from './cua-hang-delete-dialog.component';

export const cuaHangRoute: Routes = [
    {
        path: 'cua-hang',
        component: CuaHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cua-hang/:id',
        component: CuaHangDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cuaHangPopupRoute: Routes = [
    {
        path: 'cua-hang-new',
        component: CuaHangPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cua-hang/:id/edit',
        component: CuaHangPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cua-hang/:id/delete',
        component: CuaHangDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
