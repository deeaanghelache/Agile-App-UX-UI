import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardUserComponent } from './pages/dashboard-user/dashboard-user.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AllProjectsComponent } from './pages/all-projects/all-projects.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: "/dashboard",
    pathMatch: "full" 
  },
  {
    path: 'dashboard',
    component: DashboardUserComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  }, 
  {
    path: 'profile',
    component: ProfileComponent
  }, 
  {
    path: 'all',
    component: AllProjectsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
