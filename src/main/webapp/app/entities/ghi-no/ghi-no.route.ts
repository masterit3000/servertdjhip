import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GhiNoComponent } from './ghi-no.component';
import { GhiNoDetailComponent } from './ghi-no-detail.component';
import { GhiNoPopupComponent } from './ghi-no-dialog.component';
import { GhiNoDeletePopupComponent } from './ghi-no-delete-dialog.component';

export const ghiNoRoute: Routes = [
    {
        path: 'ghi-no',
        component: GhiNoComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.ghiNo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ghi-no/:id',
        component: GhiNoDetailComponent,
        data: {
            authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.ghiNo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ghiNoPopupRoute: Routes = [
    {
        path: 'ghi-no-new',
        component: GhiNoPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.ghiNo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ghi-no/:id/edit',
        component: GhiNoPopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.ghiNo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ghi-no/:id/delete',
        component: GhiNoDeletePopupComponent,
        data: {
            authorities: ['ROLE_STORE','ROLE_STAFF'],
            pageTitle: 'servertdjhipApp.ghiNo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
