import { BaseEntity } from './../../shared';

export const enum DONGTIEN {
    'CHUADONG',
    'DADONG'
}

export class LichSuDongTien implements BaseEntity {
    constructor(
        public id?: number,
        public ngaybatdau?: any,
        public ngayketthuc?: any,
        public sotien?: number,
        public trangthai?: DONGTIEN,
        public ghiChu?: string,
        public nhanVienId?: number,
        public hopDongId?: number,
    ) {
    }
}
