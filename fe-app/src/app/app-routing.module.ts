import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CourseListComponent} from "./components/course/course-list/course-list.component";
import {CoursePageComponent} from "./components/course/course-page/course-page.component";
import {LoginPageComponent} from "./components/user/login-page/login-page.component";
import {RegisterPageComponent} from "./components/user/register-page/register-page.component";
import {AuthGuard} from "./guards/auth.guard";
import {CourseCreateComponent} from "./components/course/course-create/course-create.component";
import {AdminGuard} from "./guards/admin.guard";
import {UserListComponent} from "./components/admin-pages/user-list/user-list.component";
import {MaterialListComponent} from "./components/admin-pages/material-list/material-list.component";
import {MaterialCreateComponent} from "./components/materials/material-create/material-create.component";
import {MaterialPageComponent} from "./components/materials/material-page/material-page.component";
import {MaterialEditGuard} from "./guards/material-edit.guard";
import {UserEditComponent} from "./components/user/user-edit/user-edit.component";
import {ChangePasswordComponent} from "./components/user/change-password/change-password.component";
import {MainPageComponent} from "./components/main-page/main-page.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'register',
    component: RegisterPageComponent
  },
  {
    path: 'update-user',
    component: UserEditComponent,
    canActivate: [AuthGuard]
  },{
    path: 'update-user/:id',
    component: UserEditComponent,
    canActivate: [AuthGuard, AdminGuard]
  },
  {
    path: 'change-password',
    component:ChangePasswordComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'materials',
    canActivate: [AuthGuard],
    children: [
      {
        path: ':matId',
        component: MaterialPageComponent
      },
    ]
  },
  {
    path: 'courses',
    children: [
      {
        path: 'create/:id',
        component: CourseCreateComponent,
        canActivate: [AdminGuard]
      },
      {
        path: 'create',
        component: CourseCreateComponent,
        canActivate: [AdminGuard]
      },
      {
        path: ':id',
        children: [
          {
            path: 'material',
            children: [
              {
                path: 'create',
                component: MaterialCreateComponent
              },
              {
                path: 'create/:matId',
                component: MaterialCreateComponent,
                canActivate: [MaterialEditGuard]
              },
              {
                path: ':matId',
                //TODO ADD PAGE
                component: MaterialPageComponent
              },
            ]
          },
          {
            path: '',
            component: CoursePageComponent,
          }
        ]
      },
      {
        path: '',
        component: CourseListComponent,
      }
    ],
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    children: [
      {
        path: 'users',
        component: UserListComponent
      },
      {
        path: 'materials',
        component: MaterialListComponent
      }
    ],
    canActivate: [AuthGuard, AdminGuard]
  },
  {
    path: '',
    component: MainPageComponent,
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
