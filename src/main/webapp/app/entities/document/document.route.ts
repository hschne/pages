import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {DocumentComponent, DocumentDeletePopupComponent, DocumentDetailComponent, DocumentPopupComponent} from './'
import {DocumentEditComponent} from './edit/document-edit.component';

export const documentRoute: Routes = [
    {
        path: 'document',
        component: DocumentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document/:id',
        component: DocumentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'edit',
        component: DocumentEditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
    }, {
        path: '',
        redirectTo: '/edit',
        pathMatch: 'full',
    }
];

export const documentPopupRoute: Routes = [
    {
        path: 'document-new',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/edit',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/delete',
        component: DocumentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Documents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
