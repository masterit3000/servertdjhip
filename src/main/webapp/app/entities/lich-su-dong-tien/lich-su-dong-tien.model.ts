import { BaseEntity } from './../../shared';

export const enum DONGTIEN {
    'CHUADONG',
    'DADONG',
    'TRAGOC'
}

export class LichSuDongTien implements BaseEntity {
    constructor(
        public id?: number,
        public ngaybatdau?: any,
        public ngayketthuc?: any,
        public ngaygiaodich?:any,
        public sotien?: number,
        public trangthai?: DONGTIEN,
        public ghiChu?: string,
        public mahopdong?: string,
        public nhanVienId?: number,
        public hopDongId?: number,
        public nhanVienTen?: string,
        public khachHangTen?: string,

    ) {
    }
}
