import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../interfaces/user/User";
import {NavItem} from "../../interfaces/nav-item.interface";
import {Role} from "../../interfaces/user/Role";
import {RoleAuthenticatorService} from "../../services/user-auth/role-authenticator.service";
import {AuthenticationService} from "../../services/user-auth/authentication.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  @Input() user: User;

  navItems: NavItem[] = [
    {
      name: 'Home',
      icon: 'home',
      route: '/',
      canAccess: true
    },
    {
      name: 'Account',
      icon: 'supervisor_account',
      route: '/update-user',
      canAccess: this.isAuthenticated()
    },
    {
      name: 'Courses',
      icon: 'book',
      route: '/courses',
      canAccess: true
    },
    {
      name: 'Users',
      icon: 'groups',
      route: '/admin/users',
      canAccess: this.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN]),
    },
    {
      name: 'Materials',
      icon: 'library_books',
      route: '/admin/materials',
      canAccess: this.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_MODERATOR]),
    }
  ]

  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
  }

  hasRole(role: Role) {
    // return true;
    return this.roleAuthenticatorService.hasRole(role);
  }

  hasAnyRole(roles: Role[]) {
    // return true;

    return this.roleAuthenticatorService.hasAnyRole(roles)
  }

  isAuthenticated() {
    return this.authService.isUserLoggedIn();
  }

  logout() {
    this.authService.logOut();
  }

}
