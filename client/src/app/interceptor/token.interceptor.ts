import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpClient
} from '@angular/common/http';
import { EMPTY, from, Observable, throwError, lastValueFrom } from 'rxjs';
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication/authentication.service";


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private loginUrl: string = "http://localhost:4713/ochess/login";
  private access_token: string = "";
  private verifyUrl = "http://localhost:4713/ochess/api/user/verify";
  private registerUrl = "http://localhost:4713/ochess/api/user/register";
  private sendPassResetUrl = "http://localhost:4713/ochess/api/user/send-password-reset";
  private passResetUrl = "http://localhost:4713/ochess/api/user/reset-password-request";

  constructor(private router: Router, private authenticationService: AuthenticationService, private http: HttpClient) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.access_token = window.localStorage.getItem("access_token") + "";

    if (request.url == this.loginUrl || request.url.startsWith(this.verifyUrl) || request.url == this.registerUrl ||
      request.url.startsWith(this.sendPassResetUrl) || request.url.startsWith(this.passResetUrl)) {//logins dont get intercepted
      console.log("skiped interceptor")
      return next.handle(request);
    }

    if (this.access_token == null || this.tokenExpired(this.access_token)) {
        this.router.navigate(['/login']).catch(console.error) // both access and refresh token are expired
        return EMPTY;
    }

    return next.handle(request);
  }

  private tokenExpired(token: string) {

    if(token == null){
      // this.authenticationService.setUserLoggedin(false);
      return null;
      }

      try{
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
      }catch (exception) {
        return (exception)
      }
  }
}
