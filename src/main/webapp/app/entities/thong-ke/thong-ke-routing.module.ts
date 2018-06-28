import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ThuTienVayLaiComponent } from './thu-tien-vay-lai/thu-tien-vay-lai.component';
import { ThuTienHoComponent } from './thu-tien-ho/thu-tien-ho.component';

const routes: Routes = [
  {path: 'thuTienVayLai', component:ThuTienVayLaiComponent},
  {path: 'thuTienHo', component:ThuTienHoComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThongKeRoutingModule {}
