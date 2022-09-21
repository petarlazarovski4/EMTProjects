import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {NotifierService} from "angular-notifier";
import {UserService} from "../../../services/user-auth/user.service";
import {User} from "../../../interfaces/user/User";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";
import {Option} from "../../../interfaces/option.interface";
import {CourseService} from "../../../services/course.service";
import {map} from "rxjs/operators";

@Component({
  selector: 'user',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private notifierService: NotifierService,
    private userService: UserService,
    private route: ActivatedRoute,
    private roleService: RoleAuthenticatorService,
    private courseService: CourseService
  ) {
  }

  currentUser: User;
  userId: number;
  hidePassword: boolean = true;
  userToUpdate: User;
  roles = Role;
  selectedRole: Role;
  hasRoleChangePermission: boolean = false;
  courseOption: Option[];
  userForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required]],
      role: ['', Validators.required],
      moderatingCourses: ['']
    }
  );

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    if (this.roleService.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN])) {
      this.courseService.getAllCoursesAsOption().subscribe(el => {
        this.courseOption = el;
      });
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.userId = +params['id'];
          this.userService.getUserById(this.userId).subscribe(el => {
            this.userForm.patchValue(el);
            this.userService.getModeratingCoursesByUserId(this.userId)
              .pipe(map(el => el.map(option => option.id)))
              .subscribe(el => {
                this.userForm.controls.moderatingCourses.patchValue(el);
              })
            this.selectedRole = el.role;
            this.hasRoleChangePermission = true;
          })
        } else {
          this.userForm.patchValue(this.currentUser);
        }
      });
    } else {
      this.userForm.patchValue(this.currentUser);
    }
  }


  get f() {
    return this.userForm.controls;
  }

  roleChange(role) {
    this.selectedRole = role;
  }

  onSubmit() {
    let newUser = this.userForm.value;
    this.authService.changeDetails(newUser).subscribe(
      success => {
        this.notifierService.notify('success', 'Successfully updated user details');
        if(this.selectedRole != Role.ROLE_MODERATOR) {
          this.userForm.controls.moderatingCourses.reset();
        }
      },
      error => {
        console.error(error);
        this.notifierService.notify('error', 'Error updating user.');
      }
    );
  }
}
