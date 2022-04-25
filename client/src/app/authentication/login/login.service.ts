import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl = "http://localhost:1440/ochess/login";

 constructor(private htttp: HttpClient) {
 }

 async login(emailOrUsername: string, password: string) {
   try {
     var tokens = JSON.parse(await lastValueFrom(this.htttp.post(this.loginUrl, new LoginInfo(emailOrUsername, password), {responseType: "text"})));
   
     window.localStorage.setItem("access_token", tokens.access_token)

     return true;
   } catch (exception) {
     return false;
   }
 }

}

class LoginInfo {
 constructor(emailOrUsername: string, password: string) {
   this.username = emailOrUsername;
   this.password = password;
 }

 username: string;
 password: string;

}