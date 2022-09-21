import {Component, OnInit} from '@angular/core';
import {Course} from "../../../interfaces/course.interface";
import {CourseService} from "../../../services/course.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MaterialService} from "../../../services/material.service";
import {Observable} from "rxjs";
import {Material} from "../../../interfaces/material/material.interface";
import {shareReplay} from "rxjs/operators";
import {User} from "../../../interfaces/user/User";
import {UserService} from "../../../services/user-auth/user.service";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";

@Component({
  selector: 'app-course-page',
  templateUrl: './course-page.component.html',
  styleUrls: ['./course-page.component.scss']
})
export class CoursePageComponent implements OnInit {

  course: Course;
  courseId: number;
  materials$: Observable<Material[]>
  materials: Material[];
  showAll = false;
  user: User;
  canShowAll = false;
  constructor(private service: CourseService,
              private router: Router,
              private route: ActivatedRoute,
              private materialService: MaterialService,
              private userService: UserService,
              private roleAuthService: RoleAuthenticatorService) {
  }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.route.params.subscribe(params => {
      this.courseId = +params['id'];
      this.service.getCourseById(this.courseId).subscribe(el => {
        this.course = el;
        this.canShowAll = this.roleAuthService.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN]) || this.course.moderators.filter(el => el.id == this.user.id).length > 0;
      })
      this.materials$ = this.materialService.getMaterialsByCourseId(this.courseId);
      this.refreshMaterials();
    });

  }



  showAllMaterials() {
    this.showAll = !this.showAll;
    if(this.showAll) {
      this.materials$ = this.materialService.getAllMaterialsByCourseId(this.courseId);
    } else {
      this.materials$ = this.materialService.getMaterialsByCourseId(this.courseId);
    }
    this.refreshMaterials();
  }

  get url() {
    return "/courses/create/"+this.courseId
  }

  refreshMaterials() {
    this.materials$.pipe(shareReplay(1)).subscribe(el => this.materials = el);
  }

  get materialCreateUrl() {
    return `/courses/${this.courseId}/material/create`;
  }
}
