import { BaseEntity } from './../../shared';

export const enum LOAIHOPDONG {
    'VAYLAI',
    'BATHO',
    'CAMDO'
}

export class HopDong implements BaseEntity {
    constructor(
        public id?: number,
        public ghichu?: string,
        public loaihopdong?: LOAIHOPDONG,
        public ngaytao?: any,
        public khachHangId?: number,
        public cuaHangId?: number,
        public nhanVienId?: number,
        public hopdonggocId?: number,
        public taisans?: BaseEntity[],
        public ghinos?: BaseEntity[],
        public lichsudongtiens?: BaseEntity[],
        public lichsuthaotachds?: BaseEntity[],
    ) {
    }
}
