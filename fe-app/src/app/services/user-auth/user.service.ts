import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../interfaces/user/User';
import {PagedUser} from '../../interfaces/user/PagedUser';
import {tap} from 'rxjs/operators';
import {SelfModifyUserDto} from '../../interfaces/user/SelfModifyUserDto';
import {Option} from "../../interfaces/option.interface";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    url = '/api/user';

    private currentUser: User;
    constructor(private http: HttpClient) {
    }

    load(): Promise<any>{
        const token = localStorage.getItem('token');
        if(!(token === null))
            return this.getUser().toPromise().then(
                data=>{
                    this.currentUser = data;
                }
            )
    }

    getCurrentUser(): User{
        return this.currentUser;
    }
    getUser(): Observable<User> {
        return this.http.get<User>(this.url);
    }

    getUserById(id: number): Observable<User> {
        return this.http.get<User>(`${this.url}/${id}`);
    }
    getAllUserList(
        query = ''
    ): Observable<User[]> {
        return this.http.get<User[]>(`${this.url}/list` , {
            params: new HttpParams().set('q', query)
        })
    }

    getAllUsersPaged(
        sortBy = 'id',
        sortOrder = 'asc',
        pageSize = 10,
        page = 0,
        query = ''
    ): Observable<PagedUser> {
        return this.http.get<PagedUser>(`${this.url}/paged` , {
            params: new HttpParams()
                .set('sort', `${sortBy},${sortOrder}`)
                .set('pageSize', pageSize.toString())
                .set('page', page.toString())
                .set('q', query)
        })
    }

    // modifyUser(userId: number, userDto: ModifyUserDto): Observable<User> {
    //     return this.http.put<User>(`${this.url}/${userId}`, userDto)
    // }

    modifyCurrentUser(userDto: SelfModifyUserDto): Observable<User> {
        return this.http.put<User>(this.url, userDto)
    }

    registerUser(newUser: User): Observable<User>{
        return this.http.post<User>('/api/auth/register', newUser)
    }

    getModeratingCoursesByUserId(id: number): Observable<Option[]> {
      return this.http.get<Option[]>(`${this.url}/${id}/courses`);
    }
}
