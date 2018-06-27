import { NgModule } from '@angular/core';
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

@NgModule({
  imports: [
    CommonModule,
    BaoCaoRoutingModule
  ],
  declarations: [SoQuyTienMatComponent, TongKetGiaoDichComponent, TongKetLoiNhuanComponent, ChiTietTienLaiComponent, BaoCaoDangChoVayComponent, BaoCaoHangChoThanhLyComponent, BaoCaoChuocDoDongHopDongComponent, BaoCaoThanhLyDoComponent, BaoCaoHopDongDaXoaComponent, BaoCaoTinNhanComponent, BanGiaoCaComponent, DongTienTheoNgayComponent]
})
export class BaoCaoModule { }
