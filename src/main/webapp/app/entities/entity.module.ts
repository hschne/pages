import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import {PagesDocumentModule} from "./document/document.module";

@NgModule({
    imports: [
        PagesDocumentModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesEntityModule {}
