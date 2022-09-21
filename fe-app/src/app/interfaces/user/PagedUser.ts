import {User} from './User';

export interface PagedUser {
    content: User[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
