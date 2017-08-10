import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PageService, page_routes, PageComponent, PageEditComponent, PageViewComponent} from './'

import {PagesSharedModule} from '../shared';
import {MarkdownService} from './markdown.service';
import {PageItemComponent} from './page-list-item.component';
import {PageDeleteDialogComponent, PageDeletePopupComponent} from './popup/page-delete-dialog.component';
import {PagePopupService} from './popup/page-popup.service';

@NgModule({
    imports: [
        PagesSharedModule,
        RouterModule.forRoot(page_routes, {useHash: true})
    ],
    declarations: [
        PageEditComponent,
        PageViewComponent,
        PageComponent,
        PageItemComponent,
        PageDeleteDialogComponent,
        PageDeletePopupComponent

    ],
    entryComponents: [
        PageComponent,
        PageDeleteDialogComponent,
        PageDeletePopupComponent,
    ],
    providers: [
        PageService,
        MarkdownService,
        PagePopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesMainModule {
}
