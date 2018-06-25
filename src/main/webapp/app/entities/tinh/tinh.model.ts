import { BaseEntity } from './../../shared';

export class Tinh implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public huyens?: BaseEntity[],
    ) {
    }
}
