import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NhanVienComponent } from './nhan-vien.component';
import { NhanVienDetailComponent } from './nhan-vien-detail.component';
import { NhanVienPopupComponent } from './nhan-vien-dialog.component';
import { NhanVienDeletePopupComponent } from './nhan-vien-delete-dialog.component';

export const nhanVienRoute: Routes = [
    {
        path: 'nhan-vien',
        component: NhanVienComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nhan-vien/:id',
        component: NhanVienDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nhanVienPopupRoute: Routes = [
    {
        path: 'nhan-vien-new',
        component: NhanVienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhan-vien/:id/edit',
        component: NhanVienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhan-vien/:id/delete',
        component: NhanVienDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
