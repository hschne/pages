import {Component, OnInit} from '@angular/core';
import {Account, LoginModalService, Principal} from '../../../shared';
import {JhiEventManager} from 'ng-jhipster';

@Component({
    selector: 'pages-edit',
    templateUrl: './document-edit.component.html'
})
export class DocumentEditComponent implements OnInit {

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
