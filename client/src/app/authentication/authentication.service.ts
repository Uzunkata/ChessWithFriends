import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { TokenInterceptor } from '../interceptor/token.interceptor'; 
import {Observable, of, lastValueFrom } from "rxjs";
import {count} from "rxjs/operators";
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private loginUrl = "http://localhost:4713/ochess/login";

  //@ts-ignore
  user: string;
  //TODO
  isGoogle = false;

  constructor(private http: HttpClient, private router: Router) {
    //@ts-ignore

    this.user = this.getUser(window.localStorage.getItem("access_token"));
  }


  async attemptLogin(username: string, password: string) { // true if login successful, false if failed
    try {
      var tokens = JSON.parse(await lastValueFrom(this.http.post(this.loginUrl, new LoginInfo(username, password),  {responseType: "text"})));
      window.localStorage.setItem("access_token", tokens.access_token)

      this.router.navigateByUrl('/home');
      this.user = this.getUser(tokens.access_token);
      //this.accessToken = tokens.access_token;

      window.location.reload();
      return 200;
    } catch (exception) {
      return exception;
    }

  }

  private getUser(token: string){

    if(!this.checkLogin()){
      return null;
    }

    if(token == null){
      return this.user;
    }

    var x = JSON.parse(atob(token.split('.')[1]));
    console.log(x);
    return JSON.parse(atob(token.split('.')[1]));
  }

  checkLogin() {

    let token = window.localStorage.getItem("access_token");

    if (!token){ 
      return false;
    }
    let payloadEncoded = token.split('.')[1];
    let payloadDecoded = atob(payloadEncoded);

    if (!payloadDecoded) {
      return false;
    }

    let payload = JSON.parse(payloadDecoded);
    let expDate = new Date(payload.exp * 1000);

    if(expDate > new Date()){
      return true;
    }else{
      return false;
    }
  }

}

class LoginInfo {
  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
  username: string;
  password: string;
}
