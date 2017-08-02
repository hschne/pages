import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {PageEditComponent} from './edit/page-edit.component';

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

    ]
;
