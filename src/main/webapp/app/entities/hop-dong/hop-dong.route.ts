import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopDongComponent } from './hop-dong.component';
import { HopDongDetailComponent } from './hop-dong-detail.component';
import { HopDongPopupComponent } from './hop-dong-dialog.component';
import { HopDongDeletePopupComponent } from './hop-dong-delete-dialog.component';

export const hopDongRoute: Routes = [
    {
        path: 'hop-dong',
        component: HopDongComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.hopDong.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-dong/:id',
        component: HopDongDetailComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.hopDong.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopDongPopupRoute: Routes = [
    {
        path: 'hop-dong-new',
        component: HopDongPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.hopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dong/:id/edit',
        component: HopDongPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.hopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dong/:id/delete',
        component: HopDongDeletePopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.hopDong.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
