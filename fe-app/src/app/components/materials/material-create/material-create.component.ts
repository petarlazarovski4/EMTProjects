import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Item} from "../../../interfaces/material/item.interface";
import {Material} from "../../../interfaces/material/material.interface";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MaterialService} from "../../../services/material.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotifierService} from "angular-notifier";
import {Observable} from "rxjs";

@Component({
  selector: 'app-material-create',
  templateUrl: './material-create.component.html',
  styleUrls: ['./material-create.component.scss']
})
export class MaterialCreateComponent implements OnInit {

  material: Material;
  materialId: number;
  materialForm: FormGroup;
  courseId: number;
  material$: Observable<Material>;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: MaterialService,
              private builder: FormBuilder,
              private notifierService: NotifierService) { }

  ngOnInit(): void {
    this.materialForm = this.builder.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
    this.route.params.subscribe(params => {
      if(params['matId']) {
        this.materialId = +params['matId'];
        this.material$ = this.service.getMaterialById(this.materialId);
        this.loadMaterial();
      }
      if(params['id']) {
        this.courseId = +params['id'];
      }
    });
  }

  loadMaterial() {
    this.material$.subscribe(el => {
      this.material = el;
      this.materialForm.patchValue(el);
    });
  }

  get f() {
    return this.materialForm.controls;
  }

  submit() {
    if (!this.materialForm.valid) {
      this.notifierService.notify('error', "All fields required.");
      return;
    }
    let materialData = this.materialForm.value;
    materialData.id = this.materialId;
    materialData.courseId = this.courseId;
    this.service.addOrUpdateMaterial(materialData).subscribe(el => {
      this.notifierService.notify('success', 'Material saved.');
      if(!this.materialId) {
        this.router.navigate([`${el.id}`], {relativeTo: this.route});
      }
      //success
    }, error => {
      this.notifierService.notify('error', 'Error saving material.');
    });

  }
}
