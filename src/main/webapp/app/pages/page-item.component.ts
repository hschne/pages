import {Component, Input, OnInit} from '@angular/core';
import {MarkdownService} from './markdown.service';
import {Document} from './document.model';

@Component({
    selector: 'page-list-item',
    templateUrl: './page-item.component.html',
    providers: [MarkdownService]
})
export class PageItemComponent implements OnInit {
    @Input() public document: Document;
    convertedText = '';

    constructor(private md: MarkdownService) {

    }

    ngOnInit(): void {
        this.convertedText = this.md.convert(this.document.content);
    }

}
