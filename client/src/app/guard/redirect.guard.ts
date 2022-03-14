import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppComponent } from '../app.component';
import { AuthenticationService } from '../authentication/authentication.service';


@Injectable({
  providedIn: 'root'
})
export class RedirectGuard implements CanActivate {

  curentURL: string;

  constructor(private authenticationService: AuthenticationService, private router: Router) { 
    // this.curentURL =  window.location.href;
    // console.log("this is the url dudeeeee:"+this.curentURL);
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    var isLogged = this.authenticationService.checkLogin();
    // var token = window.localStorage.getItem("access_token");
    //console.log(token);
    this.curentURL =  window.location.href;

    if (isLogged == null || isLogged == false) {
      if(this.curentURL.includes('game/')){
        this.curentURL = this.curentURL.split('localhost:4200/')[1];
        localStorage.setItem('gameURL', this.curentURL);
      }
      this.router.navigateByUrl('/login');
      console.log("you need to login");
      return false;
    }

    console.log("you are loged in");
    return true;
  }

}
