import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";
import {merge} from "rxjs";
import {MaterialsDataSource} from "../../../interfaces/material/MaterialsDataSource";
import {MaterialService} from "../../../services/material.service";
import {CourseService} from "../../../services/course.service";
import {Material} from "../../../interfaces/material/material.interface";
import {Option} from "../../../interfaces/option.interface";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'material-list',
  templateUrl: './material-list.component.html',
  styleUrls: ['./material-list.component.scss']
})
export class MaterialListComponent implements OnInit, AfterViewInit {

  search = new FormControl('');

  option = new FormControl(null);

  searchQuery = '';
  course = '';
  dataSource: MaterialsDataSource;
  displayedColumns = ['id','title', 'createdBy', 'approvedBy', 'dateCreated', 'published', 'publishAction'];
  courseOption: Option[];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private materialService: MaterialService,
              private router: Router,
              private courseService: CourseService,
              private notifierService: NotifierService) {
  }

  ngOnInit(): void {
    this.dataSource = new MaterialsDataSource(this.materialService);
    this.courseService.getAllCoursesAsOption().subscribe(el => {
      this.courseOption = el;
    });
    this.search.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(query => {
      this.searchQuery = query;
      this.dataSource.loadMaterials('id', 'asc', 10, 0, query, this.course);
    })
    this.dataSource.loadMaterials('id', 'asc', 10, 0, '', this.course);
  }

  ngAfterViewInit(): void {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(tap(() => this.loadMaterials()))
      .subscribe();
  }

  loadMaterials() {
    this.dataSource.loadMaterials(
      this.sort.active,
      this.sort.direction,
      this.paginator.pageSize,
      this.paginator.pageIndex,
      this.searchQuery,
      this.course
    );
  }

  onMaterialClick(row: any) {
    this.router.navigate(['/', 'material', row.id]);
  }

  publish(material: Material) {
    this.materialService.publish(material.id).subscribe(it => {
      this.notifierService.notify('success', 'Successfully published material');
      this.loadMaterials()
    }, error => {
      this.notifierService.notify('error', 'Error publishing material.')
    })
  }

  unPublish(material: Material) {
    this.materialService.unpublish(material.id).subscribe(it => {
      this.notifierService.notify('success', 'Successfully unpublished material');
      this.loadMaterials()
    }, error => {
      this.notifierService.notify('error', 'Error unpublishing material.')
    })
  }

  courseValueChange(event:any) {
    this.course = event;
    this.loadMaterials();
  }
}
