import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import {User} from './User';
import {PagedUser} from './PagedUser';
import {UserService} from '../../services/user-auth/user.service';

export class UsersDataSource implements DataSource<User> {

    private usersSubject = new BehaviorSubject<User[]>([]);
    private pagedUsersSubject = new BehaviorSubject<PagedUser>(null);
    protected loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private userService: UserService) {}

    get pagedUsers() {
        return this.pagedUsersSubject.value
    }

    connect(collectionViewer: CollectionViewer): Observable<User[]> {
        return this.usersSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.usersSubject.complete();
        this.loadingSubject.complete();
    }

    loadUsers(
        sortBy = "id",
        sortOrder = "asc",
        pageSize = 10,
        page = 0,
        query = ''
    ) {

        this.loadingSubject.next(true);

        this.userService.getAllUsersPaged(sortBy, sortOrder, pageSize, page, query)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((user: PagedUser) => {
                this.pagedUsersSubject.next(user)
                this.usersSubject.next(user.content)
            })

    }

}
