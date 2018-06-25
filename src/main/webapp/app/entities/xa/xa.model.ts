import { BaseEntity } from './../../shared';

export class Xa implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public huyenId?: number,
        public khachangs?: BaseEntity[],
        public nhanviens?: BaseEntity[],
        public cuahangs?: BaseEntity[],
    ) {
    }
}
