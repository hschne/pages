import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {
    DocumentService,
    page_routes,
    PageComponent,
    PageEditComponent,
    PageViewComponent
} from './'

import {PagesSharedModule} from '../shared';
import {MarkdownService} from './markdown.service';
import {PageItemComponent} from './page-item.component';

@NgModule({
    imports: [
        PagesSharedModule,
        RouterModule.forRoot(page_routes, {useHash: true})
    ],
    declarations: [
        PageEditComponent,
        PageViewComponent,
        PageComponent,
        PageItemComponent
    ],
    entryComponents: [],
    providers: [
        DocumentService,
        MarkdownService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesMainModule {
}
