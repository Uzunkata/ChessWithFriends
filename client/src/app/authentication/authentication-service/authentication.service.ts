import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Interceptor } from './interceptor/interceptor';
import { Observable, of, lastValueFrom } from "rxjs";
import { count } from "rxjs/operators";
import { Router } from '@angular/router';
import { TokensService } from '../token-service/tokens.service';
import { UserModel } from 'src/app/model/UserModel';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user: UserModel;
  private loginUrl = "http://localhost:1440/ochess/login";

  //@ts-ignore
  user: string;
  // //TODO
  // isGoogle = false;
  public gameURL: string = '';

  constructor(private tokenService: TokensService) {
    //@ts-ignore

    this.user = this.getUsername(window.localStorage.getItem("access_token"));
  }

  checkLogin() {

    let token = this.tokenService.getAccessToken();

    if (!token) return false;
    try {

      let payloadEncoded = token.split('.')[1];
      let payloadDecoded = atob(payloadEncoded);

      if (!payloadDecoded) return false;
      let payload = JSON.parse(payloadDecoded);
      let expDate = new Date(payload.exp * 1000);
      let accessIsExpired = !(expDate > new Date());

      if (accessIsExpired) {
        return false;
      } else {
        return true;
      }
    } catch (e) {
      return false;
    }
  }

  getUsername() {
    let jwt = this.tokenService.getAccessToken();
    if (jwt == null) {
      return "-"
    }
    let jwtData = jwt.split('.')[1]
    let decodedJwtJsonData = window.atob(jwtData)
    let decodedJwtData = JSON.parse(decodedJwtJsonData)
    console.log(decodedJwtData.sub)
    return decodedJwtData.sub.toString()
  }

  isTokenExpired(token: string | null): boolean {
    if (!token || token === "undefined") return true;
    try {
      // @ts-ignore
      return this.tokenService.tokenExpired(token)
    } catch (exception) {
      return true;
    }
  }
}