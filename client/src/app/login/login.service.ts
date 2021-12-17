import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl = "http://localhost:4713/ochess/login";

 constructor(private htttp: HttpClient) {
 }

 async login(username: string, password: string) {
   try {
     var tokens = JSON.parse(await lastValueFrom(this.htttp.post(this.loginUrl, new LoginInfo(username, password), {responseType: "text"})));
   
     // if (tokens.access_token == null) { // if bad credentials return false
     //   return false;
     // }
     window.localStorage.setItem("access_token", tokens.access_token)
     window.localStorage.setItem("refresh_token", tokens.refresh_token)
     return true;
   } catch (exception) {
     return false;
   }
 }

}

class LoginInfo {
 constructor(email: string, password: string) {
   this.username = email;
   this.password = password;
 }

 username: string;
 password: string;

}