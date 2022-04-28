import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { AuthenticationService } from '../authentication-service/authentication.service';
import { User } from '../../model/User';
import { RegisterService } from './register.service';
import { MessageService } from 'primeng/api';

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
  message: string = "";
  username: string;

  constructor(private registerService: RegisterService,
    private authService: SocialAuthService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private messageService: MessageService) {
    this.password = ""
    this.passwordConfirm = ""
    this.email = ""
    this.username = ""

    if (authenticationService.checkLogin()) {
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
      var message = await this.registerService.register(this.user)
      if (message == "User with this email already exists!") {
        this.messageService.add({ key: 'register', severity: 'error', summary: 'Error', detail: 'Email already taken' });
      } else if (message == "User with this username already exists!") {
        this.messageService.add({ key: 'register', severity: 'error', summary: 'Error', detail: 'Username already taken' });
      } else {
        this.password = ""
        this.passwordConfirm = ""
        this.email = ""
        this.username = ""
        this.router.navigate(['/login']).catch(console.error);
      }
    } else {
      this.messageService.add({ key: 'register', severity: 'error', summary: 'Error', detail: 'Password does not match' });

    }
  }

  googleLogin(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((data) => {
      localStorage.setItem('google_auth', JSON.stringify(data));
      this.router.navigateByUrl('/dashboard').then();
    });
  }

}