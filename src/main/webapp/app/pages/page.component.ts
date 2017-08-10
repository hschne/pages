import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Page } from './page.model';
import { PageService } from './page.service';
import {Principal} from '../shared/auth/principal.service';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';

@Component({
    selector: 'jhi-page',
    templateUrl: './page.component.html',
    styleUrls: [
        'page.scss'
    ]
})
export class PageComponent implements OnInit, OnDestroy {
    pages: Page[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private documentService: PageService,
        private alertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.documentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pages = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDocuments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Page) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInDocuments() {
        this.eventSubscriber = this.eventManager.subscribe('documentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
