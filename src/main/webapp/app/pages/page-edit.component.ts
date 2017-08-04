import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Observable} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Principal} from '../shared/index';
import {MarkdownService} from './markdown.service';
import {DocumentService} from './document.service';
import {Document} from './document.model';

@Component({
    selector: 'jhi-page-edit',
    templateUrl: './page-edit.component.html',
    providers: [MarkdownService],
    styleUrls: ['page.scss']
})
export class PageEditComponent implements OnInit {

    document: Document;
    account: Account;
    convertedText: string;
    preview = false;
    content = '';
    isSaving = false;

    constructor(private principal: Principal,
                private eventManager: JhiEventManager,
                private md: MarkdownService,
                private router: Router,
                private documentService: DocumentService,
                private alertService: JhiAlertService) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.document = new Document();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    isPreview() {
        return this.preview;
    }

    togglePreview() {
        this.preview = !this.preview
    }

    renderMarkdown(mdText: string) {
        this.convertedText = this.md.convert(mdText);
    }

    save() {
        this.isSaving = true;
        if (this.document.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentService.update(this.document));
        } else {
            this.subscribeToSaveResponse(
                this.documentService.create(this.document));
        }
    }

    private subscribeToSaveResponse(result: Observable<Document>) {
        result.subscribe((res: Document) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Document) {
        this.eventManager.broadcast({name: 'documentListModification', content: 'OK'});
        this.router.navigate(['./pages'])
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
        this.isSaving = false;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
