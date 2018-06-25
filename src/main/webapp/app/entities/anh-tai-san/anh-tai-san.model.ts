import { BaseEntity } from './../../shared';

export class AnhTaiSan implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public taiSanId?: number,
    ) {
    }
}
