import {Component, Input, OnInit} from '@angular/core';
import {MarkdownService} from './markdown.service';
import {Page} from './page.model';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

@Component({
    selector: 'page-list-item',
    templateUrl: './page-list-item.component.html',
    styleUrls: ['./page-list-item.scss'],
    providers: [MarkdownService],
    animations: [
        trigger('collapseState', [
            state('inactive', style({
                backgroundColor: '#eee',
                transform: 'scale(1)'
            })),
            state('active', style({
                backgroundColor: '#cfd8dc',
                transform: 'scale(1.1)'
            })),
            transition('inactive => active', animate('100ms ease-in')),
            transition('active => inactive', animate('100ms ease-out'))
        ])
    ]
})
export class PageItemComponent implements OnInit {
    @Input() public page: Page;
    convertedText = '';
    isCollapsed = false;

    constructor(private md: MarkdownService) {

    }

    ngOnInit(): void {
        this.convertedText = this.md.convert(this.page.content);
    }

    collapse() {
        this.isCollapsed = !this.isCollapsed;
    }

}
