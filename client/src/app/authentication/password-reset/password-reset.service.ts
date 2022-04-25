import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PasswordResetService {
  public passResetUrl = "http://localhost:1440/ochess/api/user/reset-password-request"

  constructor(private http: HttpClient) {
  }

  async doSendPasswordReset(hash: string, password: string) {
    // return await lastValueFrom(this.http.post(this.passResetUrl + "?hash=" + hash, {hash: hash,password: password}, {responseType: "text"}));
    return await this.http.post(this.passResetUrl, {
      hash: hash,
      password: password
    }, {responseType: "text", headers: {'skip': "true"}}).toPromise();
  }
}