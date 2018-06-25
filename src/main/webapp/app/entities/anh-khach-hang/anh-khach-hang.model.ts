import { BaseEntity } from './../../shared';

export class AnhKhachHang implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public khachHangId?: number,
    ) {
    }
}
