import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Page } from '../page.model';
import { PageService } from '../page.service';

@Injectable()
export class PagePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private documentService: PageService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.documentService.find(id).subscribe((page) => {
                this.documentModalRef(component, page);
            });
        } else {
            return this.documentModalRef(component, new Page());
        }
    }

    documentModalRef(component: Component, page: Page): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.page = page;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
            // When a popup is closed, currently all ways lead back to the main page
            this.router.navigate([''])
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
            this.router.navigate([''])
        });
        return modalRef;
    }
}
