import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import {PagedMaterial} from './PagedMaterial';
import {Material} from "./material.interface";
import {MaterialService} from "../../services/material.service";

export class MaterialsDataSource implements DataSource<Material> {

    private materialSubject = new BehaviorSubject<Material[]>([]);
    private pagedMaterialsSubject = new BehaviorSubject<PagedMaterial>(null);
    protected loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private materialService: MaterialService) {}

    get pagedMaterials() {
        return this.pagedMaterialsSubject.value
    }

    connect(collectionViewer: CollectionViewer): Observable<Material[]> {
        return this.materialSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.materialSubject.complete();
        this.loadingSubject.complete();
    }

    loadMaterials(
        sortBy = "id",
        sortOrder = "asc",
        pageSize = 10,
        page = 0,
        query = '',
        course = ''
    ) {

        this.loadingSubject.next(true);

        this.materialService.getAllMaterialsPaged(sortBy, sortOrder, pageSize, page, query, course)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((material: PagedMaterial) => {
                this.pagedMaterialsSubject.next(material)
                this.materialSubject.next(material.content)
            })

    }

}
