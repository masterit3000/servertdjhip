import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { FileUploadDemoComponent } from './fileuploaddemo.component';

export const fileuploadDemoRoute: Route = {
    path: 'fileupload',
    component: FileUploadDemoComponent,
    data: {
         
        pageTitle: 'primeng.fileupload.fileupload.title'
    },
    canActivate: [UserRouteAccessService]
};
