import { BaseEntity } from './../../shared';

export class NhatKy implements BaseEntity {
    constructor(
        public id?: number,
        public thoiGian?: any,
        public noiDung?: string,
        public cuaHangId?: number,
        public nhanVienId?: number,
    ) {
    }
}
