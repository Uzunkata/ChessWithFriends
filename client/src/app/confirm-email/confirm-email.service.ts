import {Injectable} from '@angular/core';
import {User} from "../model/User";
import {HttpClient} from "@angular/common/http";
import {lastValueFrom} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class ConfirmEmailService {
  verifcationUrl = "http://localhost:4713/ochess/api/user/verify"

  constructor(private http: HttpClient) {
  }

  async doConfrimEmail(hash: string, confMsg: string) {
    return await lastValueFrom(this.http.post(this.verifcationUrl + "?hash=" + hash, {responseType: "text"}));
  }
}