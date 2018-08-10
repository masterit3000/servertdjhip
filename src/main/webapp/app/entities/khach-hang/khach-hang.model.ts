import { BaseEntity } from './../../shared';
import { AnhKhachHang } from '../anh-khach-hang';

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
        public ngayTao?: Date,
        public email?: string,
        public facebook?: string,
        public ghiChu?: string,
        public tenXa?: string,
        public tenHuyen?: string,
        public tenTinh?: string,
        public xaId?: number,
        public anhs?: AnhKhachHang[],
        public hopdongs?: BaseEntity[],
        public cuaHangId?: number,
    ) {
    }
}
