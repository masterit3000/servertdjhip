import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CuaHangComponent } from './cua-hang.component';
import { CuaHangDetailComponent } from './cua-hang-detail.component';
import { CuaHangPopupComponent } from './cua-hang-dialog.component';
import { CuaHangDeletePopupComponent } from './cua-hang-delete-dialog.component';
import { TongQuatChuoiCuaHangComponent } from './tong-quat-chuoi-cua-hang/tong-quat-chuoi-cua-hang.component';
import { ThongTinChiTietCuaHangComponent } from './thong-tin-chi-tiet-cua-hang/thong-tin-chi-tiet-cua-hang.component';
import { CauHinhHangHoaComponent } from './cau-hinh-hang-hoa/cau-hinh-hang-hoa.component';
import { NhapTienQuyDauNgayComponent } from './nhap-tien-quy-dau-ngay/nhap-tien-quy-dau-ngay.component';
export const cuaHangRoute: Routes = [
    {
        path: 'cua-hang',
        component: CuaHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, 
    {
        path: 'cua-hang/:id',
        component: CuaHangDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.home.title'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: 'tongQuatChuoiCuaHang',
        component: TongQuatChuoiCuaHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.tongQuatChuoiCuaHang'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thongTinChiTietCuaHang',
        component: ThongTinChiTietCuaHangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.thongTinChiTietCuaHang'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cauHinhHangHoa',
        component: CauHinhHangHoaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.cauHinhHangHoa'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nhapTienQuyDauNgay',
        component: NhapTienQuyDauNgayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.cuaHang.nhapTienQuyDauNgay'
        },
        canActivate: [UserRouteAccessService]
    },
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
