import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VayLaiComponent } from './vay-lai.component';
import { VayLaiDetailComponent } from './vay-lai-detail.component';
import { VayLaiPopupComponent } from './vay-lai-dialog.component';
import { VayLaiDeletePopupComponent } from './vay-lai-delete-dialog.component';
import { VayLaiMoiComponent } from './vay-lai-chuc-nang/vay-lai-moi/vay-lai-moi.component';

export const vayLaiRoute: Routes = [
    {
        path: 'vay-lai',
        component: VayLaiComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vay-lai/:id',
        component: VayLaiDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vayLaiMoi',
        component: VayLaiMoiComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vayLaiPopupRoute: Routes = [
    {
        path: 'vay-lai-new',
        component: VayLaiPopupComponent,
        data: {
            authorities: ['ROLE_STORE', 'ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vay-lai/:id/edit',
        component: VayLaiPopupComponent,
        data: {
            authorities: ['ROLE_STORE'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vay-lai/:id/delete',
        component: VayLaiDeletePopupComponent,
        data: {
            authorities: ['ROLE_STORE'],
            pageTitle: 'servertdjhipApp.vayLai.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
