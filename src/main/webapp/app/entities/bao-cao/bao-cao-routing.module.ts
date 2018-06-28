import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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

const routes: Routes = [
  {
    path: 'soQuyTienMat', component: SoQuyTienMatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.soQuyTienMat'
    },
  },
  {
    path: 'tongKetGiaoDich', component: TongKetGiaoDichComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.tongKetGiaoDich'
    },
  },
  {
    path: 'tongKetLoiNhuan', component: TongKetLoiNhuanComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.tongKetLoiNhuan'
    },
  },
  {
    path: 'chiTietTienLai', component: ChiTietTienLaiComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.chiTietTienLai'
    },
  },
  {
    path: 'baoCaoDangChoVay', component: BaoCaoDangChoVayComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoDangChoVay'
    },
  },
  {
    path: 'baoCaoHangChoThanhLy', component: BaoCaoHangChoThanhLyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoHangChoThanhLy'
    },
  },
  {
    path: 'baoCaoChuocDoDongHD', component: BaoCaoChuocDoDongHopDongComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoChuocDoDongHD'
    },
  },
  {
    path: 'baoCaoThanhLyDo', component: BaoCaoThanhLyDoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoThanhLyDo'
    },
  },
  {
    path: 'baoCaoHopDongDaXoa', component: BaoCaoHopDongDaXoaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoHopDongDaXoa'
    },
  },
  {
    path: 'baoCaoTinNhan', component: BaoCaoTinNhanComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.baoCaoTinNhan'
    },
  },
  {
    path: 'banGiaoCa', component: BanGiaoCaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.banGiaoCa'
    },
  },
  {
    path: 'dongTienTheoNgay', component: DongTienTheoNgayComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.dongTienTheoNgay'
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BaoCaoRoutingModule { }
