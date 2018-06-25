import { BaseEntity } from './../../shared';

export const enum TrangThaiCuaHang {
    'DUNGHOATDONG',
    'HOATDONG'
}

export class CuaHang implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public diachi?: string,
        public dienthoai?: string,
        public trangthai?: TrangThaiCuaHang,
        public ngayTao?: any,
        public ghiChu?: string,
        public xaId?: number,
        public thuchis?: BaseEntity[],
        public khachhangs?: BaseEntity[],
        public nhanviens?: BaseEntity[],
        public hopdongs?: BaseEntity[],
    ) {
    }
}
