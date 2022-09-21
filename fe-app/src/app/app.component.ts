import {Component, OnInit} from '@angular/core';
import {UserService} from "./services/user-auth/user.service";
import {User} from "./interfaces/user/User";
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'fe-app';
  user: User;

  constructor(private userService: UserService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
  }

  back() {
    this.location.back();
  }
}
