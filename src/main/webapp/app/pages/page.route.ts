import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {PageEditComponent} from './page-edit.component';
import {PageComponent} from './page.component';
import {PageViewComponent} from './page-view.component';
import {PageDeletePopupComponent} from './popup/page-delete-dialog.component';

export const page_routes: Routes = [
        {
            path: '',
            redirectTo: '/pages',
            pathMatch: 'full'
        },
        {
            path: 'edit',
            component: PageEditComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'New Page'
            },
            canActivate: [UserRouteAccessService]
        },
        {
            path: 'edit/:id',
            component: PageEditComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'New Page'
            },
            canActivate: [UserRouteAccessService]
        },
        {
            path: 'pages',
            component: PageComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Your Pages'
            },
            canActivate: [UserRouteAccessService]
        },
        {
            path: 'view/:id',
            component: PageViewComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Page Detail'
            },
            canActivate: [UserRouteAccessService]
        },
        {
            path: 'delete/:id',
            component: PageDeletePopupComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Documents'
            },
            canActivate: [UserRouteAccessService],
            outlet: 'popup'
        }

    ]
;
