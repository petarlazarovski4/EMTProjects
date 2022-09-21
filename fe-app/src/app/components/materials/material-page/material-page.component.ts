import { Component, OnInit } from '@angular/core';
import {User} from "../../../interfaces/user/User";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {UserService} from "../../../services/user-auth/user.service";
import {Role} from "../../../interfaces/user/Role";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-material-page',
  templateUrl: './material-page.component.html',
  styleUrls: ['./material-page.component.scss']
})
export class MaterialPageComponent implements OnInit {

  user: User;
  materialId: number;
  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.route.params.subscribe(params => {
      if (params['matId']) {
        this.materialId = +params['matId'];
      }
    });
  }

  hasAnyRole(roles: Role[]) {
    return this.roleAuthenticatorService.hasAnyRole(roles)
  }

}
