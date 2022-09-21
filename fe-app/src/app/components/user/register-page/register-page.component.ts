import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ValidationErrors,
  Validators
} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../../services/user-auth/user.service";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  hidePassword: boolean = true;
  minDate: Date;
  maxDate: Date;
  userForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, this.matchValues('password')]]
    }
  );

  matchValues(
    matchTo: string // name of the control to match to
  ): (AbstractControl) => ValidationErrors | null {
    return (control: AbstractControl): ValidationErrors | null => {
      return !!control.parent &&
      !!control.parent.value &&
      control.value === control.parent.controls[matchTo].value
        ? null
        : { isMatching: false };
    };
  }

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private notifierService: NotifierService
  ) {

    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 20, 0, 1);
    this.maxDate = new Date();
  }

  ngOnInit(): void {
  }

  get f() {
    return this.userForm.controls;
  }

  onSubmit() {
    let newUser = this.userForm.value;

    this.authService.registerUser(newUser).subscribe(
      success => {
        this.notifierService.notify('success', 'Registered.');
        this.router.navigate(['/courses'])
      },
      error => {
        console.error(error);
        this.notifierService.notify('error', 'Error registering.');

      }
    );
  }

}
