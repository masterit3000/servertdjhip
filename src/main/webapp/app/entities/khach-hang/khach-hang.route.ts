import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { KhachHangComponent } from './khach-hang.component';
import { KhachHangDetailComponent } from './khach-hang-detail.component';
import { KhachHangPopupComponent } from './khach-hang-dialog.component';
import { KhachHangDeletePopupComponent } from './khach-hang-delete-dialog.component';
import { CheckThongTinKhachHangComponent } from './check-thong-tin-khach-hang/check-thong-tin-khach-hang.component';
import { KhachCanVayComponent } from './khach-can-vay/khach-can-vay.component';




export const khachHangRoute: Routes = [
    {
        path: 'khach-hang',
        component: KhachHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.khachHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'khach-hang/:id',
        component: KhachHangDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.khachHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'checkThongTinKhachHang',
        component: CheckThongTinKhachHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SS'
        },
        canActivate: [UserRouteAccessService]
    },
   
    {
        path: 'khachCanVay',
        component: KhachCanVayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SS'
        },
        canActivate: [UserRouteAccessService]
    },
   

];

export const khachHangPopupRoute: Routes = [
    {
        path: 'khach-hang-new',
        component: KhachHangPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.khachHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'khach-hang/:id/edit',
        component: KhachHangPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.khachHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'khach-hang/:id/delete',
        component: KhachHangDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.khachHang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
