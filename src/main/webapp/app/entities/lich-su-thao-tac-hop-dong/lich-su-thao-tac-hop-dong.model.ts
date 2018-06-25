import { BaseEntity } from './../../shared';

export class LichSuThaoTacHopDong implements BaseEntity {
    constructor(
        public id?: number,
        public thoigian?: any,
        public noidung?: string,
        public nhanVienId?: number,
        public hopDongId?: number,
    ) {
    }
}
