import { BaseEntity } from './../../shared';

export class TaiSan implements BaseEntity {
    constructor(
        public id?: number,
        public mota?: string,
        public sohieu?: string,
        public noicat?: string,
        public hopDongId?: number,
        public anhs?: BaseEntity[],
    ) {
    }
}
