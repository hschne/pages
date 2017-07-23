import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SampleComponent } from './sample.component';
import { SampleDetailComponent } from './sample-detail.component';
import { SamplePopupComponent } from './sample-dialog.component';
import { SampleDeletePopupComponent } from './sample-delete-dialog.component';

import { Principal } from '../../shared';

export const sampleRoute: Routes = [
    {
        path: 'sample',
        component: SampleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samples'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample/:id',
        component: SampleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samples'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const samplePopupRoute: Routes = [
    {
        path: 'sample-new',
        component: SamplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samples'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample/:id/edit',
        component: SamplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samples'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample/:id/delete',
        component: SampleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samples'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
