import {Component, OnInit} from '@angular/core';
import {Course} from "../../../interfaces/course.interface";
import {Observable} from "rxjs";
import {CourseService} from "../../../services/course.service";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {

  $courses: Observable<Course[]>;
  courses: Course[] = [];
  displayedCourses: Course[];
  search: string = '';
  canAddCourse = false;
  constructor(private _service: CourseService,
              private roleAuth: RoleAuthenticatorService) {  }

  ngOnInit(): void {
    this.$courses = this._service.getAllCourses();
    this.$courses.subscribe(el => {
      this.courses = el;
      this.displayedCourses =this.courses;
      this.canAddCourse = this.roleAuth.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN]);
    });
  }

  searchInputChange(input:string) {
    // this.dataDisplayed = this.data.filter(el => el.name.toLowerCase().includes(input.toLowerCase()) ||  el.code.toLowerCase().includes(input.toLowerCase()));
    this.displayedCourses = this.courses.filter(el => el.name.toLowerCase().includes(input.toLowerCase()) ||  el.code.toLowerCase().includes(input.toLowerCase()));
  }

}
