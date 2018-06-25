import { BaseEntity } from './../../shared';

export const enum NOTRA {
    'NO',
    'TRA'
}

export class GhiNo implements BaseEntity {
    constructor(
        public id?: number,
        public ngayghino?: any,
        public sotien?: number,
        public trangthai?: NOTRA,
        public ghiChu?: string,
        public nhanVienId?: number,
        public hopDongId?: number,
    ) {
    }
}
