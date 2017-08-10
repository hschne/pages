import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {Page} from '../page.model';
import {PagePopupService} from './page-popup.service';
import {PageService} from '../page.service';

@Component({
    selector: 'jhi-page-delete-dialog',
    templateUrl: './page-delete-dialog.component.html'
})
export class PageDeleteDialogComponent {

    page: Page;

    constructor(private documentService: PageService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager,
                private router: Router) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentListModification',
                content: 'Deleted an page'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-page-delete-popup',
    template: ''
})
export class PageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private documentPopupService: PagePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.documentPopupService
                .open(PageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
