import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {PageEditComponent} from './page-edit.component';
import {PageComponent} from './page.component';

export const page_routes: Routes = [
        {
            path: '',
            redirectTo: '/edit',
            pathMatch: 'full'
        },
        {
            path: 'edit',
            component: PageEditComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Welcome to Pages!'
            },
            canActivate: [UserRouteAccessService]
        },
        {
            path: 'pages',
            component: PageComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pages'
            },
            canActivate: [UserRouteAccessService]
        },
    ]
;
