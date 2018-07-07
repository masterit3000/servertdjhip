import { BaseEntity } from './../../shared';
import { HopDong } from '../hop-dong';

export class BatHo implements BaseEntity {
     
    constructor(
        public id?: number,
        public tienduakhach?: number,
        public tongtien?: number,
        public tongsongay?: number,
        public chuky?: number,
        public hopdongbhId?: number,
        public hopdong?: HopDong
    ) {
    }
}
