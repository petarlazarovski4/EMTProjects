import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../../interfaces/user/User';
import {UserService} from './user.service';
import {ChangePasswordPayload} from '../../interfaces/user/ChangePasswordPayload';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(
    private http: HttpClient,
    private userService: UserService
  ) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  authenticate(email: string, password: string) {

    const data = {
      "email": email,
      "password": password
    };

    return this.http.post<any>('/api/auth/login', data).pipe(
      map(
        userData => {
          const tokenStr = userData.tokenType + ' ' + userData.accessToken;
          localStorage.setItem('token', tokenStr);
          // this.userService.getUser().subscribe(
          //     user => {
          //         this.currentUserSubject.next(user)
          //         localStorage.setItem('currentUser', JSON.stringify(user))
          //     }
          // )

          return userData;
        }
      )
    )
  }

  registerUser(newUser: User): Observable<User> {
    return this.http.post<User>('/api/auth/register', newUser)
  }

  checkPassword(password: string): Observable<any> {
    return this.http.post('/api/users/auth/check-password', password);
  }

  changePassword(password: ChangePasswordPayload): Observable<any> {
    return this.http.post('/api/auth/user/password/change', password);
  }

  isUserLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    return !(token === null);
  }


  logOut() {
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
    document.location.href = '/';
  }

  changeDetails(userData: User): Observable<User> {
    return this.http.post<User>(`/api/auth/user/details`, userData);
  }

}
