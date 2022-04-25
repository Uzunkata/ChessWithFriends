import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { AuthenticationService } from '../authentication-service/authentication.service';
import { User } from '../../model/User';
import { RegisterService } from './register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  password: string;
  email: string;
  passwordConfirm: string;
  user: User = {} as User;
  message: string="";
  username: string;

  constructor(private registerService: RegisterService, private authService: SocialAuthService, private router: Router, private authenticationService: AuthenticationService) {
    this.password = ""
    this.passwordConfirm = ""
    this.email = ""
    this.username = ""

    if(authenticationService.checkLogin()){
      router.navigateByUrl("/matchmaking");
    }

  }

  ngOnInit(): void {
  }

  async doRegister() {
    if (this.password == this.passwordConfirm) {
      this.user.password = this.password;
      this.user.email = this.email;
      this.user.username = this.username;
      await this.registerService.register(this.user)
      this.password=""
      this.passwordConfirm=""
      this.email=""
      this.username=""
      this.router.navigate(['/login']).catch(console.error);
    } else this.message="Password dont match"
  }

  googleLogin(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((data) => {
      localStorage.setItem('google_auth', JSON.stringify(data));
      this.router.navigateByUrl('/dashboard').then();
    });
  }

}