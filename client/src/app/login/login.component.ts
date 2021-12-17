import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import {Toast} from "primeng/toast";
import { MessageService } from 'primeng/api';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { AuthenticationService } from '../authentication/authentication.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string = "";
  password: string = "";

  signin: FormGroup = new FormGroup({
  });

  hide = true;
  constructor(private router: Router, private messageService: MessageService,
        private authService: SocialAuthService, private authenticationService: AuthenticationService) {

          if(authenticationService.checkLogin()){
            router.navigateByUrl('/home');
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

      var request = await this.authenticationService.attemptLogin(this.email, this.password);
  
      //  window.location.replace('/home');
  
      }catch (e) {
        console.log(e)
        this.messageService.add({
                severity: 'error',
                summary: "Invalid email/username or password",
                detail: '',
              });
  
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
