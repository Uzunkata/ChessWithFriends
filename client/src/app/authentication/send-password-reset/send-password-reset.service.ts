import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SendPasswordResetService {
  public sendPassResetUrl = "http://localhost:4713/ochess/api/user/send-password-reset"

  constructor(private http: HttpClient) {
  }

  async doSendPasswordReset(email: string) {
    return await lastValueFrom(this.http.post(this.sendPassResetUrl, {email: email},{responseType: "text"}));
  }
}