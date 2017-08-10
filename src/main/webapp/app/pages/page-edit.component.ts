import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';

import {Observable} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Principal} from '../shared/index';
import {MarkdownService} from './markdown.service';
import {PageService} from './page.service';
import {Page} from './page.model';

@Component({
    selector: 'jhi-page-edit',
    templateUrl: './page-edit.component.html',
    providers: [MarkdownService],
    styleUrls: ['page-edit.scss']
})
export class PageEditComponent implements OnInit {

    routeSub: any;
    page = new Page();
    convertedText: string;
    preview = false;
    content = '';
    isSaving = false;
    title = 'New Page';

    constructor(private principal: Principal,
                private eventManager: JhiEventManager,
                private md: MarkdownService,
                private router: Router,
                private documentService: PageService,
                private alertService: JhiAlertService,
                private route: ActivatedRoute,
                private location: Location) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.documentService.find(params['id']).subscribe((page) => {
                    this.page = page;
                    this.title = 'Edit Page';
                });
            } else {
                this.page = new Page();
            }
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    isPreview() {
        return this.preview;
    }

    togglePreview() {
        if (this.convertedText === undefined) {
            this.renderMarkdown(this.page.content)
        }
        this.preview = !this.preview
    }

    renderMarkdown(mdText: string) {
        this.convertedText = this.md.convert(mdText);
    }

    save() {
        this.isSaving = true;
        if (this.page.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentService.update(this.page));
        } else {
            this.subscribeToSaveResponse(
                this.documentService.create(this.page));
        }
    }

    cancel() {
        this.location.back();
    }

    private subscribeToSaveResponse(result: Observable<Page>) {
        result.subscribe((res: Page) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Page) {
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
