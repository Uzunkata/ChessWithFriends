import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { lastValueFrom, throwError } from 'rxjs';
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SendPasswordResetService {
  public sendPassResetUrl = environment.apiUrl + "user/send-password-reset"

  constructor(private http: HttpClient) {
  }

  async doSendPasswordReset(email: string) {
    console.log(email)
    try {
      var message = await this.http.post(this.sendPassResetUrl, { "email": email },
        { responseType: "text", headers: { 'skip': "true" } }).toPromise();
    } catch (Exception) {
      return false;
    }
    return true;

    // return await this.http.post(this.sendPassResetUrl, email,
    //   {responseType: "text", headers: {'skip': "true"}}).toPromise();
  }
}
