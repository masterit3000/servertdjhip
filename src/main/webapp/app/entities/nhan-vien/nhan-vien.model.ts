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
        public tenXa?: string,
        public tenHuyen?: string,
        public tenTinh?: string,
        public cuaHangId?: number,
        public userId?: number,
        public nhatkies?: BaseEntity[],
        public thuchis?: BaseEntity[],
        public lichsudongtiens?: BaseEntity[],
        public ghinos?: BaseEntity[],
        public hopdongs?: BaseEntity[],
        public lichsuthaotacnvs?: BaseEntity[],
    ) {
    }
}
