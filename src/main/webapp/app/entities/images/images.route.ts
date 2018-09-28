import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ImagesComponent } from './images.component';
import { ImagesDetailComponent } from './images-detail.component';
import { ImagesPopupComponent } from './images-dialog.component';
import { ImagesDeletePopupComponent } from './images-delete-dialog.component';

export const imagesRoute: Routes = [
    {
        path: 'images',
        component: ImagesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.images.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'images/:id',
        component: ImagesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.images.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagesPopupRoute: Routes = [
    {
        path: 'images-new',
        component: ImagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.images.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'images/:id/edit',
        component: ImagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.images.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'images/:id/delete',
        component: ImagesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servertdjhipApp.images.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
