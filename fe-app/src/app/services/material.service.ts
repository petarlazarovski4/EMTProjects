import {Injectable} from "@angular/core";
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course.interface";
import {Material} from "../interfaces/material/material.interface";
import {PagedUser} from "../interfaces/user/PagedUser";
import {PagedMaterial} from "../interfaces/material/PagedMaterial";

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  url = 'api/materials';

  constructor(private http: HttpClient) {
  }

  getMaterialsByCourseId(courseId: number): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.url}/all/approved/${courseId}`)
  }

  getAllMaterialsByCourseId(courseId: number): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.url}/all/${courseId}`)
  }

  getMaterialById(id: number) : Observable<Material> {
    return this.http.get<Material>(`${this.url}/${id}`);
  }

  addOrUpdateMaterial(material: any): Observable<any> {
    return this.http.post(`${this.url}/create`, material);
  }

  postFile(materialId:number, fileToUpload: File): Observable<string> {
    const formData: FormData = new FormData();
    formData.append('fileKey', fileToUpload, fileToUpload.name);
    return this.http
      .post<string>(`${this.url}/${materialId}/addFile`, formData);
  }

  deleteItem(itemId: number) :Observable<string> {
    return this.http.delete<string>(`api/items/${itemId}/delete`);
  }

  addQuestion(request): Observable<string> {
    return this.http.post<string>(`${this.url}/addQuestion`, request);
  }

  unpublish(materialId: number): Observable<string> {
    return this.http.post<string>(`${this.url}/${materialId}/unpublish`, null);
  }

  publish(materialId: number): Observable<string> {
    return this.http.post<string>(`${this.url}/${materialId}/publish`, null);
  }

  upload(materialId:number, file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.url}/${materialId}/addFile`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  canAccessMaterial(materialId:number): Observable<boolean> {
    return this.http.post<boolean>(`${this.url}/can-access/${materialId}`, null);
  }

  getAllMaterialsPaged(
    sortBy = 'id',
    sortOrder = 'asc',
    pageSize = 10,
    page = 0,
    query = '',
    course
  ): Observable<PagedMaterial> {
    return this.http.get<PagedMaterial>(`${this.url}/paged` , {
      params: new HttpParams()
        .set('sort', `${sortBy},${sortOrder}`)
        .set('pageSize', pageSize.toString())
        .set('page', page.toString())
        .set('q', query)
        .set('course', course)
    })
  }

  upvoteMaterial(materialId: number): Observable<string> {
    return this.http.post<string>(`${this.url}/${materialId}/upvote`, null);
  }

  downvoteMaterial(materialId: number): Observable<string> {
    return this.http.post<string>(`${this.url}/${materialId}/downvote`, null);
  }
}
