import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BaoCaoRoutingModule } from './bao-cao-routing.module';
import { SoQuyTienMatComponent } from './so-quy-tien-mat/so-quy-tien-mat.component';
import { TongKetGiaoDichComponent } from './tong-ket-giao-dich/tong-ket-giao-dich.component';
import { TongKetLoiNhuanComponent } from './tong-ket-loi-nhuan/tong-ket-loi-nhuan.component';
import { ChiTietTienLaiComponent } from './chi-tiet-tien-lai/chi-tiet-tien-lai.component';
import { BaoCaoDangChoVayComponent } from './bao-cao-dang-cho-vay/bao-cao-dang-cho-vay.component';
import { BaoCaoHangChoThanhLyComponent } from './bao-cao-hang-cho-thanh-ly/bao-cao-hang-cho-thanh-ly.component';
import { BaoCaoChuocDoDongHopDongComponent } from './bao-cao-chuoc-do-dong-hop-dong/bao-cao-chuoc-do-dong-hop-dong.component';
import { BaoCaoThanhLyDoComponent } from './bao-cao-thanh-ly-do/bao-cao-thanh-ly-do.component';
import { BaoCaoHopDongDaXoaComponent } from './bao-cao-hop-dong-da-xoa/bao-cao-hop-dong-da-xoa.component';
import { BaoCaoTinNhanComponent } from './bao-cao-tin-nhan/bao-cao-tin-nhan.component';
import { BanGiaoCaComponent } from './ban-giao-ca/ban-giao-ca.component';
import { DongTienTheoNgayComponent } from './dong-tien-theo-ngay/dong-tien-theo-ngay.component';
import { BaoCaoBatHoComponent } from './bao-cao-bat-ho/bao-cao-bat-ho.component';
import { BaoCaoVayLaiComponent } from './bao-cao-vay-lai/bao-cao-vay-lai.component';
import { TableModule } from '../../../../../../node_modules/primeng/table';
import { CalendarModule } from '../../../../../../node_modules/primeng/primeng';
import { LichSuDongTienService } from '../lich-su-dong-tien';
import { FormsModule } from '@angular/forms';
import { ServertdjhipSharedModule } from '../../shared';
@NgModule({
    imports: [CommonModule,
        BaoCaoRoutingModule,
        TableModule,
        CalendarModule,
        FormsModule,
        ServertdjhipSharedModule,
    ],
    declarations: [
        SoQuyTienMatComponent,
        TongKetGiaoDichComponent,
        TongKetLoiNhuanComponent,
        ChiTietTienLaiComponent,
        BaoCaoDangChoVayComponent,
        BaoCaoHangChoThanhLyComponent,
        BaoCaoChuocDoDongHopDongComponent,
        BaoCaoThanhLyDoComponent,
        BaoCaoHopDongDaXoaComponent,
        BaoCaoTinNhanComponent,
        BanGiaoCaComponent,
        DongTienTheoNgayComponent,
        BaoCaoBatHoComponent,
        BaoCaoVayLaiComponent
    ],
    providers: [LichSuDongTienService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BaoCaoModule { }
