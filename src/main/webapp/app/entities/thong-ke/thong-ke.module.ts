import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ThongKeRoutingModule } from './thong-ke-routing.module';
import { ThuTienVayLaiComponent } from './thu-tien-vay-lai/thu-tien-vay-lai.component';
import { ThuTienHoComponent } from './thu-tien-ho/thu-tien-ho.component';
import { TableModule } from '../../../../../../node_modules/primeng/table';
import { CalendarModule } from '../../../../../../node_modules/primeng/primeng';
import { FormsModule } from '../../../../../../node_modules/@angular/forms';
import { ServertdjhipSharedModule } from '../../shared';
import { LichSuDongTienService } from '../lich-su-dong-tien';

@NgModule({
  imports: [
    CommonModule,
    ThongKeRoutingModule,
    TableModule,
    CalendarModule,
    FormsModule,
    ServertdjhipSharedModule,
  ],
  declarations: [ThuTienVayLaiComponent, ThuTienHoComponent],
  providers: [LichSuDongTienService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ThongKeModule { }
