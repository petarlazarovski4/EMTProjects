import {Role} from "./Role";

export interface User {
    id?: number;
    username?: string;
    email?: string;
    firstName?: string;
    lastName?: string;
    role?: Role;
}
