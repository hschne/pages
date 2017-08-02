import {Component, OnInit} from '@angular/core';

import {JhiEventManager} from 'ng-jhipster';
import {Principal} from '../shared/index';
import {MarkdownService} from './markdown.service';

@Component({
    selector: 'jhi-page-edit',
    templateUrl: './page-edit.component.html',
    providers: [MarkdownService]
})
export class PageEditComponent implements OnInit {

    account: Account;
    convertedText: string;
    preview = false;

    constructor(private principal: Principal,
                private eventManager: JhiEventManager,
                private md: MarkdownService) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
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
}
