import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TokensService {

  private refreshUrl = environment.apiUrl + "user/token/refresh";

  constructor(private http: HttpClient) {
  }

  saveAccessToken(access_token: any) {
    window.localStorage.setItem("access_token", access_token);
  }

  getAccessToken() {
    return window.localStorage.getItem("access_token");
  }

  removeAccessToken() {
    window.localStorage.removeItem("access_token");
  }

  tokenExpired(token: string) {
    if (token == null) {
      return null;
    }
    try {
      const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
      return (Date.now() >= expiry * 1000);
    } catch (exception) {
      return true
    }
  }
}