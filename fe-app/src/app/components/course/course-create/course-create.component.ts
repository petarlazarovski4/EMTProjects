import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from 'angular-notifier';
import {Course} from "../../../interfaces/course.interface";
import {CourseService} from "../../../services/course.service";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-course-create',
  templateUrl: './course-create.component.html',
  styleUrls: ['./course-create.component.scss']
})
export class CourseCreateComponent implements OnInit {

  courseForm: FormGroup;
  courseId: number;
  course: Course;

  constructor(private _builder: FormBuilder,
              private _router: Router,
              private _route: ActivatedRoute,
              private _service: CourseService,
              private _notifierService: NotifierService) { }

  ngOnInit(): void {
    this.courseForm = this._builder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      code: ['', Validators.required]
    });

    this._route.params.subscribe(params => {
      if(params['id']) {
        this.courseId = +params['id'];
        this._service.getCourseById(this.courseId).subscribe(el => {
          this.course = el;
          this.courseForm.patchValue(el);
        });
      }
    });

  }

  onSubmit() {

    if (!this.courseForm.valid) {
      this._notifierService.notify('error', "All fields required.");
      return;
    }
    let courseData = this.courseForm.value;
    courseData.id = this.courseId;
    this._service.addOrUpdateCourse(courseData).subscribe(el => {
      this._notifierService.notify('success', 'Course saved.');
      this._router.navigate(["/courses"]);
      //success
    }, error => {
      this._notifierService.notify('error', 'Error saving course.');
    });

  }

  get f() {
    return this.courseForm.controls;
  }
}
