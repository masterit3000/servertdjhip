import { BaseEntity } from './../../shared';

export const enum TrangThaiKhachHang {
    'HOATDONG',
    'DUNGHOATDONG',
    'CAPDO1',
    'CAPDO2',
    'CAPDO3'
}

export class KhachHang implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public diachi?: string,
        public dienthoai?: string,
        public cmnd?: string,
        public trangthai?: TrangThaiKhachHang,
        public ngayTao?: any,
        public email?: string,
        public facebook?: string,
        public ghiChu?: string,
        public xaId?: number,
        public anhs?: BaseEntity[],
        public hopdongs?: BaseEntity[],
        public cuaHangId?: number,
    ) {
    }
}
