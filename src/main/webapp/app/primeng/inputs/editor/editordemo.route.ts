import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { EditorDemoComponent } from './editordemo.component';

export const editorDemoRoute: Route = {
    path: 'editor',
    component: EditorDemoComponent,
    data: {
         
        pageTitle: 'primeng.inputs.editor.title'
    },
    canActivate: [UserRouteAccessService]
};
