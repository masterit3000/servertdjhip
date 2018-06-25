import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LichSuDongTienComponent } from './lich-su-dong-tien.component';
import { LichSuDongTienDetailComponent } from './lich-su-dong-tien-detail.component';
import { LichSuDongTienPopupComponent } from './lich-su-dong-tien-dialog.component';
import { LichSuDongTienDeletePopupComponent } from './lich-su-dong-tien-delete-dialog.component';

export const lichSuDongTienRoute: Routes = [
    {
        path: 'lich-su-dong-tien',
        component: LichSuDongTienComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuDongTien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lich-su-dong-tien/:id',
        component: LichSuDongTienDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuDongTien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lichSuDongTienPopupRoute: Routes = [
    {
        path: 'lich-su-dong-tien-new',
        component: LichSuDongTienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuDongTien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lich-su-dong-tien/:id/edit',
        component: LichSuDongTienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuDongTien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lich-su-dong-tien/:id/delete',
        component: LichSuDongTienDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuDongTien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
