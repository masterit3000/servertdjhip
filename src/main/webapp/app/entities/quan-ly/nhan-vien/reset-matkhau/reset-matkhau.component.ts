import { Component, OnInit } from '@angular/core';
import { Principal } from '../../../../shared';

@Component({
  selector: 'jhi-reset-matkhau',
  templateUrl: './reset-matkhau.component.html',
  styles: []
})
export class ResetMatkhauComponent implements OnInit {
  doNotMatch: string;
    error: string;
    success: string;
    account: any;
    password: string;
    confirmPassword: string;

    constructor(
        private principal: Principal
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
    }

    
}
