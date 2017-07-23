import { BaseEntity, User } from './../../shared';

export class Document implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public content?: any,
        public user?: User,
    ) {
    }
}
