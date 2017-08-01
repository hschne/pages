import {Component, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, NavigationEnd, Router} from '@angular/router';

import {Title} from '@angular/platform-browser';
import {StateStorageService} from '../../shared';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    styleUrls: [
        'main.scss'
    ]
})
export class JhiMainComponent implements OnInit {

    constructor(private titleService: Title,
                private router: Router,
                private $storageService: StateStorageService) {
    }

    ngOnInit() {

        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'pagesApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }
}
