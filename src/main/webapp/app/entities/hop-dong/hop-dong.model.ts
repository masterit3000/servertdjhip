import { BaseEntity } from './../../shared';

export const enum LOAIHOPDONG {
    'VAYLAI',
    'BATHO',
    'CAMDO'
}
export const enum TRANGTHAIHOPDONG{
    'QUAHAN',
    'DANGVAY',
    'DADONG',
}

export class HopDong implements BaseEntity {
    // public id?: number;
    // public mahopdong?: string;
    // public ghichu?: string;
    // public loaihopdong?: LOAIHOPDONG;
    // public ngaytao?: any;
    // public khachHangId?: number;
    // public cuaHangId?: number;
    // public nhanVienId?: number;
    // public hopdonggocId?: number;
    // public taisans?: BaseEntity[];
    // public ghinos?: BaseEntity[];
    // public lichsudongtiens?: BaseEntity[];
    // public lichsuthaotachds?: BaseEntity[];
    constructor(
        public id?: number,
        public mahopdong?: string,
        public ghichu?: string,
        public loaihopdong?: LOAIHOPDONG,
        public ngaytao?: any,
        public khachHangId?: number, // khachHangId
        public khachHangTen?: string,
        public cuaHangId?: number,
        public nhanVienId?: number,
        public nhanVienTen?: string,
        public hopdonggocId?: number,
        public songaytracham?: number,
        public sotientracham?: number,
        public taisans?: BaseEntity[],
        public ghinos?: BaseEntity[],
        public lichsudongtiens?: BaseEntity[],
        public lichsuthaotachds?: BaseEntity[]
    ) {
        this.mahopdong = '';
    }
}
