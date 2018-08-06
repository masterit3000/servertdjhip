import { BaseEntity } from './../../shared';
import { NhanVien } from '../nhan-vien';

export class LichSuThaoTacHopDong implements BaseEntity {
    constructor(
        public id?: number,
        public thoigian?: any,
        public noidung?: string,
        public nhanVienId?: number,
        public nhanVienTen?: string,
        public hopDongId?: number,
        public soTienGhiNo?:number,
        public soTienGhiCo?:number,


    ) {
    }
}
