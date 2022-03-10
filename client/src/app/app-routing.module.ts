import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './board/board.component';
import { ConfirmEmailComponent } from './confirm-email/confirm-email.component';
import { IsNotLoggedGuard } from './guard/is-not-logged.guard';
import { RedirectGuard } from './guard/redirect.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MatchmakingComponent } from './matchmaking/matchmaking.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { RegisterComponent } from './register/register.component';
import { SendPasswordResetComponent } from './send-password-reset/send-password-reset.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
  path: 'login',
  component: LoginComponent,
  canActivate: [IsNotLoggedGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
      canActivate: [IsNotLoggedGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [RedirectGuard]
  },
  {
    path: 'confirm-email',
    component: ConfirmEmailComponent
  },
  {
    path: 'password-reset',
    component: PasswordResetComponent
  },
  {
    path: 'send-password-reset',
    component: SendPasswordResetComponent
  },
  {
    path: 'matchmaking',
  component: MatchmakingComponent,
  canActivate: [RedirectGuard]
  },
  {
    path:'game/:gameUUID',
    component: BoardComponent,
    canActivate: [RedirectGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
