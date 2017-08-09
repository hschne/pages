import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MarkdownService} from './markdown.service';
import {DocumentService} from './document.service';
import {Document} from './document.model';
import {Subscription} from 'rxjs';
import {JhiEventManager} from 'ng-jhipster';

@Component({
    selector: 'page-view',
    templateUrl: './page-view.component.html',
    providers: [MarkdownService],
    styleUrls: ['page-view.scss']
})
export class PageViewComponent implements OnInit {

    routeSub: any;
    document: Document = new Document();
    convertedText: string;
    eventSubscriber: Subscription;

    constructor(private md: MarkdownService,
                private documentService: DocumentService,
                private route: ActivatedRoute,
                private eventManager: JhiEventManager,
                private router: Router) {
    }

    ngOnInit(): void {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.documentService.find(params['id']).subscribe((document) => {
                    this.document = document;
                    this.convertedText = this.md.convert(document.content);
                });
            }
        });
        this.registerChangeInDocuments();
    }

    registerChangeInDocuments() {
        this.eventSubscriber = this.eventManager.subscribe('documentListModification', (response) => this.nav());
    }

    nav() {
        this.router.navigate(['/pages']);
    }

}
