import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {UserService} from '../services/user-auth/user.service';
import {Role} from "../interfaces/user/Role";

@Injectable({
    providedIn: 'root'
})
export class AdminGuard implements CanActivate {
    constructor(
        private userService: UserService
    ) {
    }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean {
        return this.matchAnyRole([Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN])
    }

    private matchAnyRole(roles: Role[]): boolean {
      return roles.filter(el => this.userService.getCurrentUser().role == el).length != 0;
    }
}
