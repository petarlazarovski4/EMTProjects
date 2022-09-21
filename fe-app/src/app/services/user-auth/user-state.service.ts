import {Injectable} from '@angular/core';
import {User} from '../../interfaces/user/User';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    private account$: BehaviorSubject<User> = new BehaviorSubject<User>(undefined);

    get account() {
        return this.account$.value;
    }

    set account(user: User) {
        this.account$.next(user);
    }
}
