import { Component, OnInit } from '@angular/core';
import { Principal } from '../../../../../shared';
import { ResetPasswordService } from './reset-password.service';

@Component({
    selector: 'jhi-reset-password',
    templateUrl: './reset-password.component.html'
})
export class ResetPasswordComponent implements OnInit {
    doNotMatch: string;
    error: string;
    success: string;
    account: any;
    password: string;
    confirmPassword: string;

    constructor(
        private resetPasswordService: ResetPasswordService,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
    }

    changePassword() {
        if (this.password !== this.confirmPassword) {
            this.error = null;
            this.success = null;
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.resetPasswordService.save(this.password).subscribe(
                () => {
                    this.error = null;
                    this.success = 'OK';
                },
                () => {
                    this.success = null;
                    this.error = 'ERROR';
                }
            );
        }
    }
}
