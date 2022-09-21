import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course.interface";
import {Option} from "../interfaces/option.interface";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  url = 'api/courses';

  constructor(private http: HttpClient) {
  }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.url}/all`);
  }

  getCourseById(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.url}/${courseId}`)
  }

  addOrUpdateCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(`${this.url}/add`, course);
  }

  getAllCoursesAsOption(): Observable<Option[]> {
    return this.http.get<Option[]>(`${this.url}/option`);
  }

}
