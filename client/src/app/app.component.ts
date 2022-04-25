import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthenticationService } from './authentication/authentication-service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  isLogedin = false;
  // isGoogle = false;
  username: string = "";

  constructor(private router: Router, private authenticationService: AuthenticationService, private messageService: MessageService){
    this.isLogedin = authenticationService.checkLogin();
    // this.isGoogle = authenticationService.isGoogle;
    this.username = authenticationService.getUsername();
  }

  login() {
    this.router.navigate(['/login']).catch(console.error);
  }

  register() {
    this.router.navigate(['/register']).catch(console.error);
  }

  matchHistory(){
    this.router.navigate(['/match-history']).catch(console.error);
  }

  matchmaking(){
    this.router.navigate(['/matchmaking']).catch(console.error);

  }

  logout() {
    window.localStorage.removeItem("access_token");
    // window.localStorage.removeItem("refresh_token");
    this.isLogedin = false;
    window.location.reload();
    window.localStorage.clear();

    window.location.replace('/login');
  }

  public showToast(title: string, message: string, isError: boolean) {
    this.messageService.add({
      severity: isError ? 'error' : 'success',
      summary: title,
      detail: message
    })
  }
}
