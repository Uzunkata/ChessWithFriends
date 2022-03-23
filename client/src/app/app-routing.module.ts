import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './game/board/board.component';
import { ConfirmEmailComponent } from './authentication/confirm-email/confirm-email.component';
import { IsNotLoggedGuard } from './authentication/guard/is-not-logged.guard';
import { RedirectGuard } from './authentication/guard/redirect.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './authentication/login/login.component';
import { MatchHistoryComponent } from './match-history/match-history.component';
import { MatchmakingComponent } from './game/matchmaking/matchmaking.component';
import { PasswordResetComponent } from './authentication/password-reset/password-reset.component';
import { RegisterComponent } from './authentication/register/register.component';
import { SendPasswordResetComponent } from './authentication/send-password-reset/send-password-reset.component';

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
    path: 'match-history',
    component: MatchHistoryComponent,
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
