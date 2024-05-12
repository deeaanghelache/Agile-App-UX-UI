import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthenticationService } from 'src/app/services/userAuthentication/user-authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  public registerTitle:String = "Sign Up";
  public registerForm!:FormGroup;
  public userAlreadyExists:String = "";
  public ok:boolean = false;
  public tryAgain: String = "";

  constructor(private formBuilder:FormBuilder, private router: Router, private userAuthentication:UserAuthenticationService) {}

  passwordAndConfirmPasswordChecker : ValidatorFn = (group: AbstractControl):  ValidationErrors | null => { 
    let password = group.get('password')?.value;
    let confirmPassword = group.get('confirmPassword')?.value
    return password === confirmPassword ? null : { passwordsMismatched: true }
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group(
    {      
        firstName : ['', Validators.required],
        lastName : ['', Validators.required],
        email : ['', Validators.compose([Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])],
        password : ['', [Validators.required, Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,}$")]],
        confirmPassword : ['', Validators.required]
      }, { validators: [this.passwordAndConfirmPasswordChecker] }
    );
  }

  registerUserFunction() {
    if (this.registerForm.valid){
      this.ok = true;
      this.userAuthentication.register(
        this.registerForm.value
      ).subscribe((response : any) => {
          if (response != null){
             this.router.navigateByUrl('/login');
          } else { 
             // User already exists
             this.registerForm.reset();
             this.userAlreadyExists = "Username or Email already exists";
          }
      })
    }
    else {
      this.ok = false;
      this.tryAgain = "Some of the data you typed didn't pass the validation tests. Please, be careful when completing this form.";
      this.registerForm.reset();
    }
  }
  
}
