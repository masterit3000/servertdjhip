import { BaseEntity } from './../../shared';
import { Huyen } from '../huyen';

export class Tinh implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public huyens?: Huyen[],
    ) {
    }
}
