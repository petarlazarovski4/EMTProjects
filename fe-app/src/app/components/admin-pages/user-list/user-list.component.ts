import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {UsersDataSource} from "../../../interfaces/user/UsersDataSource";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {UserService} from "../../../services/user-auth/user.service";
import {merge} from "rxjs";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit, AfterViewInit {

  search = new FormControl('');

  searchQuery = '';
  dataSource: UsersDataSource;
  displayedColumns = ['id','username', 'firstName', 'lastName', 'role', 'edit'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.dataSource = new UsersDataSource(this.userService);
    this.search.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(query => {
      this.searchQuery = query;
      this.dataSource.loadUsers('id', 'asc', 10, 0, query);
    })
    this.dataSource.loadUsers();
  }

  ngAfterViewInit(): void {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(tap(() => this.loadUsers()))
      .subscribe();

  }

  loadUsers() {
    this.dataSource.loadUsers(
      this.sort.active,
      this.sort.direction,
      this.paginator.pageSize,
      this.paginator.pageIndex,
      this.searchQuery
    );
  }

  onUserClick(row: any) {
    this.router.navigate(['/', 'update-user', row.id]);
  }


}
