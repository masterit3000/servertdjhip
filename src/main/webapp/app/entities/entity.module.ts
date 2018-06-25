import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ServertdjhipTinhModule } from './tinh/tinh.module';
import { ServertdjhipHuyenModule } from './huyen/huyen.module';
import { ServertdjhipXaModule } from './xa/xa.module';
import { ServertdjhipKhachHangModule } from './khach-hang/khach-hang.module';
import { ServertdjhipCuaHangModule } from './cua-hang/cua-hang.module';
import { ServertdjhipNhanVienModule } from './nhan-vien/nhan-vien.module';
import { ServertdjhipAnhKhachHangModule } from './anh-khach-hang/anh-khach-hang.module';
import { ServertdjhipHopDongModule } from './hop-dong/hop-dong.module';
import { ServertdjhipLichSuDongTienModule } from './lich-su-dong-tien/lich-su-dong-tien.module';
import { ServertdjhipBatHoModule } from './bat-ho/bat-ho.module';
import { ServertdjhipVayLaiModule } from './vay-lai/vay-lai.module';
import { ServertdjhipGhiNoModule } from './ghi-no/ghi-no.module';
import { ServertdjhipLichSuThaoTacHopDongModule } from './lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.module';
import { ServertdjhipThuChiModule } from './thu-chi/thu-chi.module';
import { ServertdjhipTaiSanModule } from './tai-san/tai-san.module';
import { ServertdjhipAnhTaiSanModule } from './anh-tai-san/anh-tai-san.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ServertdjhipTinhModule,
        ServertdjhipHuyenModule,
        ServertdjhipXaModule,
        ServertdjhipKhachHangModule,
        ServertdjhipCuaHangModule,
        ServertdjhipNhanVienModule,
        ServertdjhipAnhKhachHangModule,
        ServertdjhipHopDongModule,
        ServertdjhipLichSuDongTienModule,
        ServertdjhipBatHoModule,
        ServertdjhipVayLaiModule,
        ServertdjhipGhiNoModule,
        ServertdjhipLichSuThaoTacHopDongModule,
        ServertdjhipThuChiModule,
        ServertdjhipTaiSanModule,
        ServertdjhipAnhTaiSanModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipEntityModule {}
