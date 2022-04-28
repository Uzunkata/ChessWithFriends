import { HttpClient, HttpErrorResponse } from '@angular/common/http';
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
    // try{
    return await this.http.post(this.registerUrl, user, {responseType: "text"}).toPromise().catch((err: HttpErrorResponse) => {
      // simple logging, but you can do a lot more, see below
      return err.error;
    });
    // }catch (err: HttpErrorResponse){
    //   return err.message;
    // }
  }
}
