import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';


@Injectable({
  providedIn: 'root'
})
export class RedirectGuard implements CanActivate {

  constructor( private authenticationService :AuthenticationService, private router: Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      var isLogged = this.authenticationService.checkLogin();
      var user = window.localStorage.getItem("access_token");
      //console.log(user);

      if(isLogged == null || isLogged == false){
        this.router.navigateByUrl('/login');
        console.log("you need to login");
        return false;
      }
      
    console.log("you are login");
    return true;
  }
  
}
