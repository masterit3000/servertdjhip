import { BaseEntity } from './../../shared';

export const enum THUCHI {
    'THU',
    'CHI',
    'GOPVON',
    'RUTVON'
}

export class ThuChi implements BaseEntity {
    constructor(
        public id?: number,
        public noidung?: string,
        public thoigian?: any,
        public thuchi?: THUCHI,
        public cuaHangId?: number,
        public nhanVienId?: number,
    ) {
    }
}
