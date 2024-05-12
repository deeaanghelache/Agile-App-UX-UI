import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthenticationService } from 'src/app/services/userAuthentication/user-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public loginTitle:String = "Sign Up";
  public loginForm!:FormGroup;
  public userNotFound:String = "";
  public ok:boolean = false;
  public tryAgain: String = "";
  public userId!:any;
  public currentEmail = "";
  public userRole = "";

  constructor(private formBuilder:FormBuilder, private router: Router, private userAuthentication:UserAuthenticationService) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group(
      {      
          email : ['', Validators.compose([Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])],
          password : ['', [Validators.required]]
      }
    );
  }

  userCurrentSessionUpdate(){
    sessionStorage.setItem("email", this.currentEmail);
    sessionStorage.setItem("role", this.userRole); 
    sessionStorage.setItem("logged", "true");
    sessionStorage.setItem("userId", this.userId);
  }

  loginUserFunction() {
    if (this.loginForm.valid) {
      this.ok = true;
      this.userAuthentication.login(
        this.loginForm.value
      ).subscribe((response : any) => {
        if (response != null) {
          this.userId = response['userId'];
          this.currentEmail = response['email'];
          this.userAuthentication.checkIfAdmin(response['userId'])
            .subscribe((secondResponse : any) => {
              if (secondResponse == true) {
                this.userRole = "admin";
              } else {
                this.userAuthentication.checkIfScrumMaster(response['userId'])
                  .subscribe((thirdResponse : any) => {
                    if (thirdResponse == true) {
                      this.userRole = "scrum";
                    }
                  }) 
              }
            })
          this.userCurrentSessionUpdate();
          this.router.navigateByUrl("/dashboard");
        } else {
          this.loginForm.reset();
          this.userNotFound = "Email or password incorrect!";
        }
      })
    }
  }
}
