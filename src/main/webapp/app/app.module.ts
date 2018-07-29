import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {
    Ng2Webstorage,
    LocalStorageService,
    SessionStorageService
} from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';
import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { ServertdjhipSharedModule, UserRouteAccessService } from './shared';
import { ServertdjhipAppRoutingModule } from './app-routing.module';
import { ServertdjhipHomeModule } from './home/home.module';
import { ServertdjhipAdminModule } from './admin/admin.module';
import { ServertdjhipAccountModule } from './account/account.module';
import { ServertdjhipEntityModule } from './entities/entity.module';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { ServertdjhipAppHiThereModule } from './hi-there/hi-there.module';
// import { ServertdjhipprimengModule } from './primeng/primeng.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { SidebarModule } from 'primeng/primeng';
import { QuanLyComponent } from './quan-ly/quan-ly.component';
import { BatHoComponent } from './quan-ly/bat-ho/bat-ho.component';
import { VayLaiComponent } from './quan-ly/vay-lai/vay-lai.component';
import { KhachHangComponent } from './quan-ly/khach-hang/khach-hang.component';
import { CuaHangComponent } from './quan-ly/cua-hang/cua-hang.component';
import { NhanVienComponent } from './quan-ly/nhan-vien/nhan-vien.component';
import { ThongKeComponent } from './quan-ly/thong-ke/thong-ke.component';
import { BaoCaoComponent } from './quan-ly/bao-cao/bao-cao.component';

@NgModule({
    imports: [
        BrowserModule,
        ServertdjhipAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        ServertdjhipSharedModule,
        ServertdjhipHomeModule,
        ServertdjhipAdminModule,
        ServertdjhipAccountModule,
        ServertdjhipEntityModule,
        // ServertdjhipprimengModule,
        ServertdjhipAppHiThereModule,
        SidebarModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        QuanLyComponent,
        BatHoComponent,
        VayLaiComponent,
        KhachHangComponent,
        CuaHangComponent,
        NhanVienComponent,
        ThongKeComponent,
        BaoCaoComponent
    ],
    providers: [
        ProfileService,
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class ServertdjhipAppModule {}
