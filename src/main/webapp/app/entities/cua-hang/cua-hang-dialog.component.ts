import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { CuaHangPopupService } from './cua-hang-popup.service';
import { CuaHangService } from './cua-hang.service';
import { Xa, XaService } from '../xa';
import { Tinh, TinhService } from '../tinh';
import { Huyen, HuyenService } from '../huyen';
import { UserService, User } from '../../shared';

@Component({
    selector: 'jhi-cua-hang-dialog',
    templateUrl: './cua-hang-dialog.component.html'
})
export class CuaHangDialogComponent implements OnInit {

    cuaHang: CuaHang;
    isSaving: boolean;

    xas: Xa[];
    filteredXas: Xa[];
    filteredTinhs: Tinh[];
    filteredHuyens: Huyen[];
    
    tinhs: Tinh[];
    huyens: Huyen[];
    xa: Xa;
    tinh: Tinh;
    huyen: Huyen;
    users: User[];
    user: User;
    ketoan: any;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cuaHangService: CuaHangService,
        private xaService: XaService,
        private tinhService: TinhService,
        private userService: UserService,
        private huyenService: HuyenService,
        private eventManager: JhiEventManager

    ) {
        this.user = new User;
    }

    ngOnInit() {
        this.isSaving = false;
        this.xaService.query()
            .subscribe((res: HttpResponse<Xa[]>) => { this.xas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.loadAllKeToan();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.cuaHang.xaId = this.xa.id;
        this.cuaHang.keToanId = this.user.id;
        console.log(this.cuaHang.keToanId);
        
        if (this.cuaHang.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cuaHangService.update(this.cuaHang));
        } else {
            this.subscribeToSaveResponse(
                this.cuaHangService.create(this.cuaHang));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CuaHang>>) {
        result.subscribe((res: HttpResponse<CuaHang>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CuaHang) {
        this.eventManager.broadcast({ name: 'cuaHangListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() { 
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackXaById(index: number, item: Xa) {
        return item.id;
    }
    filterXas(event: any) {
        const query = event.query;
        console.log(query);
        this.filteredXas = this.filterXa(query, this.huyen.xas);
        // this.xaService.getXa(query).subscribe((xas: any) => {
        //     this.filteredXas = this.filterXa(query, xas);
        // });
    }
    filterXa(query: any, xas: Xa[]): Xa[] {
        const filtered: any[] = [];
        for (const Xa of xas) {
            if (Xa.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Xa);
            }
        }
        return filtered;
    }
    filterTinhs(event: any) {
        const query = event.query;
        console.log(query);
        this.tinhService.getTinh(query).subscribe((tinhs: any) => {
            
            console.log(tinhs);
            // this.filteredTinhs = this.filterTinh(query, tinhs);
            this.filteredTinhs =  tinhs;

        });
    }
    filterTinh(query: any, tinhs: Tinh[]): Tinh[] {
        const filtered: any[] = [];
        for (const Tinh of tinhs) {
            if (Tinh.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Tinh);
            }
        }
        return filtered;
    }
    filterHuyens(event: any) {
        const query = event.query;
        console.log(query);
        this.filteredHuyens =this.filterHuyen(query, this.tinh.huyens);
        // this.huyenService.getHuyenByTinh(query, this.tinh.id).subscribe((huyens: any) => {
        //     this.filteredHuyens = this.filterHuyen(query, huyens);
        // });
    }
    filterHuyen(query: any, huyens: Huyen[]): Huyen[] {
        const filtered: any[] = [];
        for (const Huyen of huyens) {
            if (Huyen.ten.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(Huyen);
            }
        }
        return filtered;
    }
    loadAllKeToan(){
        this.userService.findAllKeToan().subscribe(
            (res: HttpResponse<User[]>) => {
                this.users = res.body;
                console.log(this.user.id);
                
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}

@Component({
    selector: 'jhi-cua-hang-popup',
    template: ''
})
export class CuaHangPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cuaHangPopupService: CuaHangPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cuaHangPopupService
                    .open(CuaHangDialogComponent as Component, params['id']);
            } else {
                this.cuaHangPopupService
                    .open(CuaHangDialogComponent as Component);
        
                }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
