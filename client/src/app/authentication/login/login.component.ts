import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { Toast } from "primeng/toast";
import { MessageService } from 'primeng/api';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthenticationService } from '../authentication-service/authentication.service';
import { LoginService } from './login.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  emailOrUsername: string = "";
  password: string = "";

  signin: FormGroup = new FormGroup({
  });

  hide = true;
  constructor(private router: Router,
    private authService: SocialAuthService,
    private loginService: LoginService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService) {

    if (authenticationService.checkLogin()) {
      router.navigateByUrl('/matchmaking');
    }
  }

  ngOnInit(): void {
  }

  get emailInput() {
    return this.signin.get('username');
  }
  get passwordInput() {
    return this.signin.get('password');
  }

  async attemptLogin() {
    try {
      var message = await this.loginService.login(this.emailOrUsername, this.password)
      if (!message) {
        this.messageService.add({ key: 'login', severity: 'error', summary: 'Error', detail: 'Bad Credentials' });
      } else if(message == "not verified") {
        this.messageService.add({ key: 'login', severity: 'error', summary: 'Error', detail: 'User Not Verified' });
      }else {
        window.location.reload();
        this.router.navigate(['matchmaking'])
      }
    } catch (Exception) {
      this.messageService.add({ key: 'login', severity: 'error', summary: 'Error', detail: 'User not verified' });
      console.log(Exception)
    }
  }

  register() {
    this.router.navigate(['/register']).catch(console.error);
  }

  googleLogin(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((data) => {
      localStorage.setItem('google_auth', JSON.stringify(data));
      this.router.navigateByUrl('/dashboard').then();
    });
  }

  forgotPassword() {
    this.router.navigate(['/send-password-reset'])
  }
}
