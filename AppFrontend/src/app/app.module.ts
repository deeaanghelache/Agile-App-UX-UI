import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardUserComponent } from './pages/dashboard-user/dashboard-user.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatButtonModule} from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTooltipModule} from '@angular/material/tooltip';
import { ProjectPageComponent } from './pages/project-page/project-page.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AllProjectsComponent } from './pages/all-projects/all-projects.component'; 

@NgModule({
  declarations: [
    AppComponent,
    DashboardUserComponent,
    LoginComponent,
    RegisterComponent,
    ProjectPageComponent,
    ProfileComponent,
    AllProjectsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule, 
    MatFormFieldModule,
    MatInputModule, 
    MatButtonModule,
    BrowserAnimationsModule, 
    MatTooltipModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
