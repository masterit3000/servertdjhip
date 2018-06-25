import { BaseEntity } from './../../shared';

export const enum TrangThaiNhanVien {
    'DUNGHOATDONG',
    'NHANVIEN',
    'QUANLYCUAHANG',
    'QUANTRI'
}

export class NhanVien implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public diachi?: string,
        public dienthoai?: string,
        public cmnd?: string,
        public trangthai?: TrangThaiNhanVien,
        public ngayTao?: any,
        public ghiChu?: string,
        public xaId?: number,
        public cuaHangId?: number,
        public userId?: number,
        public thuchis?: BaseEntity[],
        public lichsudongtiens?: BaseEntity[],
        public ghinos?: BaseEntity[],
        public hopdongs?: BaseEntity[],
        public lichsuthaotacnvs?: BaseEntity[],
    ) {
    }
}
