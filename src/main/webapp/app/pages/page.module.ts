import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PagesSharedModule} from '../shared';

import {PageEditComponent} from './edit/page-edit.component';
import {page_routes} from './page.route';

@NgModule({
    imports: [
        PagesSharedModule,
        RouterModule.forRoot(page_routes, {useHash: true})
    ],
    declarations: [
        PageEditComponent
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesMainModule {
}
