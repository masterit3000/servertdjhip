import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LichSuThaoTacHopDongComponent } from './lich-su-thao-tac-hop-dong.component';
import { LichSuThaoTacHopDongDetailComponent } from './lich-su-thao-tac-hop-dong-detail.component';
import { LichSuThaoTacHopDongPopupComponent } from './lich-su-thao-tac-hop-dong-dialog.component';
import { LichSuThaoTacHopDongDeletePopupComponent } from './lich-su-thao-tac-hop-dong-delete-dialog.component';

export const lichSuThaoTacHopDongRoute: Routes = [
    {
        path: 'lich-su-thao-tac-hop-dong',
        component: LichSuThaoTacHopDongComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuThaoTacHopDong.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lich-su-thao-tac-hop-dong/:id',
        component: LichSuThaoTacHopDongDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuThaoTacHopDong.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lichSuThaoTacHopDongPopupRoute: Routes = [
    {
        path: 'lich-su-thao-tac-hop-dong-new',
        component: LichSuThaoTacHopDongPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuThaoTacHopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lich-su-thao-tac-hop-dong/:id/edit',
        component: LichSuThaoTacHopDongPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuThaoTacHopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lich-su-thao-tac-hop-dong/:id/delete',
        component: LichSuThaoTacHopDongDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.lichSuThaoTacHopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
