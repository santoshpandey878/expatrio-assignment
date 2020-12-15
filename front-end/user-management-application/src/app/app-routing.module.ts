import { UserDetailsComponent } from './user-details/user-details.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './_helpers/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'add', component: CreateUserComponent, canActivate: [AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'update/:id', component: UpdateUserComponent, canActivate: [AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'details/:id', component: UserDetailsComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
