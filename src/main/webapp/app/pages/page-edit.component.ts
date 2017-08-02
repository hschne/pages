import {Component, OnInit} from '@angular/core';

import {JhiEventManager} from 'ng-jhipster';
import {Principal} from '../shared/index';
import {LoginModalService} from '../shared/login/login-modal.service';

@Component({
    selector: 'page-edit',
    templateUrl: './page-edit.component.html'
})
export class PageEditComponent implements OnInit {

    account: Account;

    constructor(private principal: Principal,
                private loginModalService: LoginModalService,
                private eventManager: JhiEventManager) {
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
}
