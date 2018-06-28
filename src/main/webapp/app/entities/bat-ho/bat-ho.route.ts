import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BatHoComponent } from './bat-ho.component';
import { BatHoDetailComponent } from './bat-ho-detail.component';
import { BatHoPopupComponent } from './bat-ho-dialog.component';
import { BatHoDeletePopupComponent } from './bat-ho-delete-dialog.component';
import { BatHoMoiComponent } from './bat-ho-chuc-nang/bat-ho-moi/bat-ho-moi.component';

export const batHoRoute: Routes = [
    {
        path: 'bat-ho',
        component: BatHoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.batHo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bat-ho/:id',
        component: BatHoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.batHo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'batHoMoi',
        component: BatHoMoiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sssasd'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const batHoPopupRoute: Routes = [
    {
        path: 'bat-ho-new',
        component: BatHoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.batHo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bat-ho/:id/edit',
        component: BatHoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.batHo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bat-ho/:id/delete',
        component: BatHoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.batHo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
