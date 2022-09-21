import {Material} from "./material.interface";

export interface PagedMaterial {
    content: Material[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
