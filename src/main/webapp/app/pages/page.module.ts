import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {
    DocumentService,
    page_routes,
    PageComponent,
    PageEditComponent
} from './'

import {PagesSharedModule} from '../shared';

@NgModule({
    imports: [
        PagesSharedModule,
        RouterModule.forRoot(page_routes, {useHash: true})
    ],
    declarations: [
        PageEditComponent,
        PageComponent
    ],
    entryComponents: [],
    providers: [
        DocumentService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesMainModule {
}
