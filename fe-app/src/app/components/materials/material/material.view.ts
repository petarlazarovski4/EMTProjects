import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Material} from "../../../interfaces/material/material.interface";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {User} from "../../../interfaces/user/User";
import {UserService} from "../../../services/user-auth/user.service";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";
import {Observable} from "rxjs";
import {MaterialService} from "../../../services/material.service";
import {Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import {shareReplay} from "rxjs/operators";

@Component({
  selector: 'material',
  templateUrl: './material.view.html',
  styleUrls: ['./material.view.scss']
})
export class MaterialView implements OnInit {

  @Input() materialId: number;
  @Input() canOpenFull: boolean;
  @Input() courseId: number;
  @Input() set material(mat: Material){
    this._material = mat;
  };
  private _material: Material;
  get material() {
    return this._material;
  }
  user: User;
  @Output() refresh: EventEmitter<boolean> = new EventEmitter();
  material$: Observable<Material>
  canEditMaterial = false;
  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private userService: UserService,
    private service: MaterialService,
    private notifierService: NotifierService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.material$ = this.service.getMaterialById(this.materialId != null ? this.materialId : this.material.id)

    this.loadMaterial();
  }

  hasAnyRole(roles: Role[]) {
    return this.roleAuthenticatorService.hasAnyRole(roles)
  }

  get hasUnPublishPermission() {
    return this.hasAnyRole([Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN, Role.ROLE_MODERATOR]);
  }

  loadMaterial() {
    this.material$.pipe(shareReplay(1)).subscribe(el => {
      this._material = el
      this.canEditMaterial = this.hasAnyRole([Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN, Role.ROLE_MODERATOR]) || this.material.createdBy === this.user.username;
    });
  }

  unpublishMaterial() {
    let matId = this.materialId != null ? this.materialId : this.material.id;
    this.service.unpublish(matId).subscribe(it => {
      this.notifierService.notify('success', 'Successfully unpublished material');
      this.refresh.emit(true);
    }, error => {
      this.notifierService.notify('error', 'Error unpublishing material.')
    })
  }


  publishMaterial() {
    let matId = this.materialId != null ? this.materialId : this.material.id;
    this.service.publish(matId).subscribe(it => {
      this.notifierService.notify('success', 'Successfully published material');
      this.refresh.emit(true);
    }, error => {
      this.notifierService.notify('error', 'Error publishing material.')
    })
  }

  openFullPage() {
    if (this.canOpenFull) {
      this.router.navigate(['/courses', this.courseId, 'material', this.material.id]);
    }
  }

  upvoteMaterial(material: Material) {
    this.service.upvoteMaterial(material.id).subscribe(el => {
      this.notifierService.notify('success', 'Liked material');
      this.loadMaterial()
    }, error => {
      this.notifierService.notify('error', error.error);
    })
  }

  downvoteMaterial(material: Material) {
    this.service.downvoteMaterial(material.id).subscribe(el => {
      this.notifierService.notify('success', 'Disliked material');
      this.loadMaterial()
    }, error => {
      this.notifierService.notify('error', error.error);
    })
  }

  get editUrl() {
    let matId = this.materialId != null ? this.materialId : this.material.id;
    if(matId != null) {
      return '/courses/' + this.courseId + '/material/create/' +matId.toString();
    }
  }
}
