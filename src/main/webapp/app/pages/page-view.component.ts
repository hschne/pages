import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MarkdownService} from './markdown.service';
import {PageService} from './page.service';
import {Page} from './page.model';

@Component({
    selector: 'page-view',
    templateUrl: './page-view.component.html',
    providers: [MarkdownService],
    styleUrls: ['page-view.scss']
})
export class PageViewComponent implements OnInit {

    routeSub: any;
    page: Page = new Page();
    convertedText: string;

    constructor(private md: MarkdownService,
                private pageService: PageService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.pageService.find(params['id']).subscribe((page) => {
                    this.page = page;
                    this.convertedText = this.md.convert(page.content);
                });
            }
        });
    }
}
