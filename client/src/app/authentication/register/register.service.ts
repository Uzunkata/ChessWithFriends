import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/User';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private  registerUrl="http://localhost:1440/ochess/api/user/register";

  constructor(private http: HttpClient) {
  }

  async register(user: User) {
    // console.log(await this.http.post(this.registerUrl, user, {responseType: "text"}));
    return await this.http.post(this.registerUrl, user, {responseType: "text"}).toPromise();
  }
}
