import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ThuTienVayLaiComponent } from './thu-tien-vay-lai/thu-tien-vay-lai.component';
import { ThuTienHoComponent } from './thu-tien-ho/thu-tien-ho.component';

const routes: Routes = [
  {
    path: 'thuTienVayLai', component: ThuTienVayLaiComponent,
    data: {
      authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
      pageTitle: 'Thống kê  vay lãi'
    },
  },
  {
    path: 'thuTienHo', component: ThuTienHoComponent,
    data: {
      authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
      pageTitle: 'Thống kê bát họ'
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThongKeRoutingModule { }
