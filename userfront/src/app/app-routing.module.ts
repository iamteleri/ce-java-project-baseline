import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { HomeGuard } from './guards/home.guard';
import { LoginGuard } from './guards/login.guard';
import { DetailUserComponent } from './users/detail-user/detail-user.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';
import { ListUserComponent } from './users/list-user/list-user.component';
import { NewUserComponent } from './users/new-user/new-user.component';

const routes: Routes = [
{ path: '', component: ListUserComponent, canActivate: [HomeGuard], data: {expectedRoles : ['admin']}},
{ path: 'detail/:id', component: DetailUserComponent, canActivate: [HomeGuard], data: {expectedRoles : ['admin', 'user']}},
{ path: 'new', component: NewUserComponent, canActivate: [HomeGuard], data: {expectedRoles : ['admin']}},
{ path: 'edit/:id', component: EditUserComponent, canActivate: [HomeGuard], data: {expectedRoles : ['admin', 'user']}},
{ path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
{ path: '**', redirectTo: '', pathMatch: 'full' }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
