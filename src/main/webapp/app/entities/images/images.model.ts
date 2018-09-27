import { BaseEntity } from './../../shared';

export class Images implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
