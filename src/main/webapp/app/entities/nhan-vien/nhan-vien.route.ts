import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NhanVienComponent } from './nhan-vien.component';
import { NhanVienDetailComponent } from './nhan-vien-detail.component';
import { NhanVienPopupComponent } from './nhan-vien-dialog.component';
import { NhanVienDeletePopupComponent } from './nhan-vien-delete-dialog.component';
import { PhanQuyenNhanVienComponent } from './phan-quyen-nhan-vien/phan-quyen-nhan-vien.component';

export const nhanVienRoute: Routes = [
    {
        path: 'nhan-vien',
        component: NhanVienComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nhan-vien/:id',
        component: NhanVienDetailComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'phanQuyenNhanVien',
        component: PhanQuyenNhanVienComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.nhanVien.phanQuyenNhanVien'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nhanVienPopupRoute: Routes = [
    {
        path: 'nhan-vien-new',
        component: NhanVienPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_ADMIN','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhan-vien/:id/edit',
        component: NhanVienPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_ADMIN'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nhan-vien/:id/delete',
        component: NhanVienDeletePopupComponent,
        data: {
            authorities: ['ROLE_STORE'],
            pageTitle: 'servertdjhipApp.nhanVien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
