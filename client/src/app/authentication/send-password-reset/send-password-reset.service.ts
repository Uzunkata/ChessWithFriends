import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { lastValueFrom } from 'rxjs';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SendPasswordResetService {
  public sendPassResetUrl = environment.apiUrl +"user/send-password-reset"

  constructor(private http: HttpClient) {
  }

  async doSendPasswordReset(email: string) {
    console.log(email)
    return await this.http.post(this.sendPassResetUrl, {"email": email},
      {responseType: "text", headers: {'skip': "true"}}).toPromise();
    // return await this.http.post(this.sendPassResetUrl, email,
    //   {responseType: "text", headers: {'skip': "true"}}).toPromise();
  }
}
