import { BaseEntity } from './../../shared';

export class Huyen implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public tinhId?: number,
        public xas?: BaseEntity[],
    ) {
    }
}
