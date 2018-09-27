import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ThuTienVayLaiComponent } from './thu-tien-vay-lai/thu-tien-vay-lai.component';
import { ThuTienHoComponent } from './thu-tien-ho/thu-tien-ho.component';

const routes: Routes = [
  {
    path: 'thuTienVayLai', component: ThuTienVayLaiComponent,
    data: {
      authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
      pageTitle: 'global.menu.thuTienVayLai'
    },
  },
  {
    path: 'thuTienHo', component: ThuTienHoComponent,
    data: {
      authorities: ['ROLE_USER','ROLE_STORE','ROLE_STAFF'],
      pageTitle: 'global.menu.thuTienHo'
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThongKeRoutingModule { }
