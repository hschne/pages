import {BaseEntity} from '../shared/model/base-entity';
import {User} from '../shared/user/user.model';

export class Page implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public content?: any,
        public user?: User,
    ) {
    }
}
