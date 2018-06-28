import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ThongKeRoutingModule } from './thong-ke-routing.module';
import { ThuTienVayLaiComponent } from './thu-tien-vay-lai/thu-tien-vay-lai.component';
import { ThuTienHoComponent } from './thu-tien-ho/thu-tien-ho.component';

@NgModule({
  imports: [
    CommonModule,
    ThongKeRoutingModule
  ],
  declarations: [ThuTienVayLaiComponent, ThuTienHoComponent]
})
export class ThongKeModule { }
