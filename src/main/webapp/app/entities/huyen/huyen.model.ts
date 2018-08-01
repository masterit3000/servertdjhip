import { BaseEntity } from './../../shared';
import { Xa } from '../xa';

export class Huyen implements BaseEntity {
    constructor(
        public id?: number,
        public ten?: string,
        public tinhId?: number,
        public xas?: Xa[],
    ) {
    }
}
