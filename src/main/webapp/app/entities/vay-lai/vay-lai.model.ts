import { BaseEntity } from './../../shared';
import { HopDong } from '../hop-dong';
export const enum HINHTHUCLAI {
    'NGAY',
    'THANG',
    'TUAN',
    'NAM',
    'THANGCODINH'
}

export const enum TINHLAI {
    'MOTTRIEU',
    'CHUKY',
    'PHANTRAM'
}

export class VayLai implements BaseEntity {
    constructor(
        public id?: number,
        public tienvay?: number,
        public hinhthuclai?: HINHTHUCLAI,
        public thoigianvay?: number,
        public chukylai?: number,
        public lai?: number,
        public cachtinhlai?: TINHLAI,
        public thulaitruoc?: boolean,
        public hopdongvlId?: number,
        public hopdongvl?: HopDong,
    ) {
        this.thulaitruoc = false;
    }
}
