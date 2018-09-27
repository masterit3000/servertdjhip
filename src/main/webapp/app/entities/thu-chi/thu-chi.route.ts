import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ThuChiComponent } from './thu-chi.component';
import { ThuChiDetailComponent } from './thu-chi-detail.component';
import { ThuChiPopupComponent } from './thu-chi-dialog.component';
import { ThuChiDeletePopupComponent } from './thu-chi-delete-dialog.component';
import { ThuHoatDongComponent } from './thu-hoat-dong/thu-hoat-dong.component';
import { ChiHoatDongComponent } from './chi-hoat-dong/chi-hoat-dong.component';
import { GopVonComponent } from './gop-von/gop-von.component';
import { RutVonComponent } from './rut-von/rut-von.component';

export const thuChiRoute: Routes = [
    {
        path: 'thu-chi',
        component: ThuChiComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thu-chi/:id',
        component: ThuChiDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thuHoatDong',
        component: ThuHoatDongComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.thuHoatDong'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chiHoatDong',
        component: ChiHoatDongComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.chiHoatDong'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gopVon',
        component: GopVonComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.gopVon'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rutVon',
        component: RutVonComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.rutVon'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const thuChiPopupRoute: Routes = [
    {
        path: 'thu-chi-new',
        component: ThuChiPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'thu-chi/:id/edit',
        component: ThuChiPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'thu-chi/:id/delete',
        component: ThuChiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.thuChi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
