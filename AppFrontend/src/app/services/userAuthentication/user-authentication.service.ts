import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class UserAuthenticationService {
  private privateHttpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      observe: 'response'
    }),
  };
  private baseUrl: string = environment.backendUrl;

  constructor(private http: HttpClient) { }

  register(user: any){
    return this.http.post(
      this.baseUrl + 'user/register', 
      user
    );
  }

  login(user: any){
    return this.http.post(
      this.baseUrl + 'user/login',
      user, 
      this.privateHttpHeaders
    )
  }

  checkIfAdmin(userId : any) {
    return this.http.get(
      this.baseUrl + "userRole/checkAdmin/" + userId,
      this.privateHttpHeaders
    )
  }

  checkIfScrumMaster(userId : any) {
    return this.http.get(
      this.baseUrl + "userRole/checkScrumMaster/" + userId,
      this.privateHttpHeaders
    )
  }
}