import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Document } from './document.model';
import { DocumentService } from './document.service';
import {Principal} from '../shared/auth/principal.service';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';

@Component({
    selector: 'jhi-document',
    templateUrl: './page.component.html',
    styleUrls: [
        'page.scss'
    ]
})
export class PageComponent implements OnInit, OnDestroy {
documents: Document[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private documentService: DocumentService,
        private alertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.documentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.documents = res.json;
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

    trackId(index: number, item: Document) {
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
