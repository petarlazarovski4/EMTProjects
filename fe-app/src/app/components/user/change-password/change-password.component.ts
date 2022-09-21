import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private notifierService: NotifierService
  ) { }

  passwordForm: FormGroup;

  hidePassword: boolean = true;
  ngOnInit(): void {
    this.passwordForm =  this.formBuilder.group({
        oldPassword: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required, this.matchValues('password')]]
      }
    );
  }
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

  get f() {
    return this.passwordForm.controls;
  }

  onSubmit() {
    let passwordPayload = this.passwordForm.value;

    // this.notifierService.notify('info', NotifierMessages.SENDING_DATA);

    this.authService.changePassword(passwordPayload).subscribe(
      success => {
        this.notifierService.notify('success', 'Successfully updated password');
        this.router.navigate(["/update-user"]);
      },
      error => {
        console.error(error);
        this.notifierService.notify('error', 'Error updating password');

      }
    );
  }
}
