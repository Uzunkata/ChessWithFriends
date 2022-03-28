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
import {AuthenticationService} from "../authentication.service";
import { TokensService } from '../../token-service/tokens.service';
import { environment } from 'src/environments/environment';


@Injectable()
export class Interceptor implements HttpInterceptor {

  private loginUrl: string = "http://localhost:4713/ochess/login";
  private access_token: string | null = "";
  private verifyUrl = "http://localhost:4713/ochess/api/user/verify";
  private registerUrl = "http://localhost:4713/ochess/api/user/register";
  private sendPassResetUrl = "http://localhost:4713/ochess/api/user/send-password-reset";
  private passResetUrl = "http://localhost:4713/ochess/api/user/reset-password-request";

  constructor(private router: Router, private tokenService: TokensService, private http: HttpClient) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.access_token = this.tokenService.getAccessToken();

    if (environment.urlsToSkip.includes(request.url.replace(environment.apiUrl, "")) || request.url == environment.loginUrl || request.url.includes("/verify")) {
      console.log("skiped interceptor: "+request.url)
      return next.handle(request);
    }

    if (this.accessTokenInvalid()) {
        this.router.navigate(['/login']).catch(console.error)
        return EMPTY;
    }

    request = this.modifyRequest(request, this.access_token)
    return next.handle(request);
  }

  private modifyRequest(req: HttpRequest<any>, token: any) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ` + token
      }
    });
    return req
  }

  accessTokenInvalid() {
    return this.access_token == null || this.tokenService.tokenExpired(this.access_token)
  }
}
