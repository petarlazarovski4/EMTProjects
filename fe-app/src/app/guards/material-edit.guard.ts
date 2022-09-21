import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {UserService} from '../services/user-auth/user.service';
import {Role} from "../interfaces/user/Role";
import {MaterialService} from "../services/material.service";
import {Observable, of} from "rxjs";
import {catchError, map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class MaterialEditGuard implements CanActivate {
    constructor(
        private userService: UserService,
        private materialService: MaterialService,
        private router: Router
    ) {
    }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const materialId = +next.paramMap.get('matId');
        if(this.matchAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_MODERATOR])) return true;
        return this.materialService.canAccessMaterial(materialId).pipe(map(el=> {
          if(el) {
            return true;
          }
        }, catchError(err => {
          //FIX NAVIGATION!!!
          this.router.navigate(['/']);
          return of(false);
        })))
    }

    private matchAnyRole(roles: Role[]): boolean {
      return roles.filter(el => this.userService.getCurrentUser().role == el).length != 0;
    }

}
