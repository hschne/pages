import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PagesSharedModule} from '../shared';

import {
    accountState,
    ActivateComponent,
    ActivateService,
    LoginComponent,
    PasswordComponent,
    PasswordResetFinishComponent,
    PasswordResetFinishService,
    PasswordResetInitComponent,
    PasswordResetInitService,
    PasswordService,
    PasswordStrengthBarComponent,
    Register,
    RegisterComponent,
    SessionsComponent,
    SessionsService,
    SettingsComponent
} from './';
import {FooterComponent} from '../layouts/footer/footer.component';

@NgModule({
    imports: [
        PagesSharedModule,
        RouterModule.forRoot(accountState, {useHash: true}),
    ],
    declarations: [
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SessionsComponent,
        SettingsComponent,
        LoginComponent
    ],
    providers: [
        SessionsService,
        Register,
        ActivateService,
        PasswordService,
        PasswordResetInitService,
        PasswordResetFinishService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesAccountModule {
}
