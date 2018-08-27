import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { UserService, User } from '../../../../shared';
import { NhanVienService, NhanVien } from '../../../nhan-vien';
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
    nhanVien: NhanVien;
    user: User;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userService: UserService,
        private nhanVienService: NhanVienService,

        private route: ActivatedRoute
    ) { }

    changePassword(){
        
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }
    load(id) {
        this.nhanVienService.find(id)
            .subscribe((nhanVienResponse: HttpResponse<NhanVien>) => {
                this.nhanVien = nhanVienResponse.body;

                this.userService.find(this.nhanVien.userLogin)
                    .subscribe((userResponse: HttpResponse<User>) => {
                        this.user = userResponse.body;
                    }
                    );
            });

    }


}
