import { BaseEntity } from './../../shared';

export const enum LOAIHOPDONG {
    'VAYLAI',
    'BATHO',
    'CAMDO'
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
        public cuaHangId?: number,
        public nhanVienId?: number,
        public hopdonggocId?: number,
        public taisans?: BaseEntity[],
        public ghinos?: BaseEntity[],
        public lichsudongtiens?: BaseEntity[],
        public lichsuthaotachds?: BaseEntity[]
    ) {
        this.mahopdong = '';
    }
}
