import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {User} from '../../interfaces/user/User';
import {Role} from "../../interfaces/user/Role";

@Injectable({
    providedIn: 'root'
})
export class RoleAuthenticatorService {

    private currentUser: User;

    constructor(private userService: UserService) {
        this.currentUser = userService.getCurrentUser()
    }

    hasRole(role: Role) {
        return this.currentUser ? (this.currentUser.role === role): false
    }

    hasAnyRole(roles: Role[]) {

        return this.currentUser ? (roles.includes(this.currentUser.role)): false
    }

}
