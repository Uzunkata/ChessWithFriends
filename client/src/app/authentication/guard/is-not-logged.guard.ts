import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication-service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class IsNotLoggedGuard implements CanActivate {

  curentURL: string;

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (!this.authenticationService.checkLogin()) {
      return true;
    }

     this.curentURL = localStorage.getItem('gameURL') || "";

    if(localStorage.getItem('gameURL')){
      this.router.navigateByUrl(this.curentURL);
      localStorage.removeItem('gameURL')
    }else{
      this.router.navigateByUrl('/matchmaking');
    }

    return false;
  }

}
