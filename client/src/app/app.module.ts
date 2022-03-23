import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import { GoogleLoginProvider } from 'angularx-social-login';
import { FormsModule } from "@angular/forms";
import { environment } from 'src/environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './authentication/login/login.component';
import { AccordionModule } from 'primeng/accordion';     //accordion and accordion tab
import { MenuItem } from 'primeng/api';
import { MessageService } from 'primeng/api';

// import { HttpModule } from '@angular/http';
// import { ModalModule } from 'angular2-modal';
// import { BootstrapModalModule } from 'angular2-modal/plugins/bootstrap';
// import { PromotionModal } from './promotion-modal';
import { v4 as uuidv4 } from 'uuid';


import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { SplitterModule } from 'primeng/splitter';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';

import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';

import { SlideMenuModule } from 'primeng/slidemenu';
import { RippleModule } from 'primeng/ripple';
import { CheckboxModule } from 'primeng/checkbox';
import { StepsModule } from 'primeng/steps';
import { CardModule } from 'primeng/card';
import { PasswordModule } from "primeng/password";
import { MenubarModule } from "primeng/menubar";
//import { MatInputModule, MatTableModule, MatPaginatorModule, MatSortModule } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatDividerModule } from "@angular/material/divider";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatFormFieldModule } from "@angular/material/form-field";
//  import {EditorModule} from "primeng/editor";
import { AutoCompleteModule } from "primeng/autocomplete";
import { ChipsModule } from "primeng/chips";
import { SelectButtonModule } from "primeng/selectbutton";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { ConfirmEmailComponent } from './authentication/confirm-email/confirm-email.component';
import { RegisterComponent } from './authentication/register/register.component';
import { HomeComponent } from './home/home.component';
import { SendPasswordResetComponent } from './authentication/send-password-reset/send-password-reset.component';
import { PasswordResetComponent } from './authentication/password-reset/password-reset.component';
import { BoardComponent } from './game/board/board.component';
import { MatchmakingComponent } from './game/matchmaking/matchmaking.component';
import { MatchHistoryComponent } from './match-history/match-history.component';


// import {AngularMaterialModule} from './angular-material/angular-material.module';
// import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
// import {AngularDraggableModule} from 'angular2-draggable';
// import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
// import {InterceptorService} from "./security/interceptor.service";
// import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
// import {PerfectScrollbarConfigInterface} from 'ngx-perfect-scrollbar';
// import {MomentModule} from 'ngx-moment';
// import {DragDropModule} from "@angular/cdk/drag-drop";




const CLIENT_ID = '109523009378-pr5h9fdgvf10rs0csm985k3d108e4c1f.apps.googleusercontent.com';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ConfirmEmailComponent,
    RegisterComponent,
    HomeComponent,
    SendPasswordResetComponent,
    PasswordResetComponent,
    // PromotionModal,
    BoardComponent,
    MatchmakingComponent,
    MatchHistoryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    SocialLoginModule,
    AppRoutingModule,
    AccordionModule,
    RouterModule.forRoot([]),

    
    // ModalModule.forRoot(),
    // BootstrapModalModule,

    BrowserAnimationsModule,
    MessagesModule,
    MessageModule,
    ToastModule,
    TieredMenuModule,
    SplitterModule,
    HttpClientModule,
    BrowserAnimationsModule,
    TableModule,
    ButtonModule,
    ToolbarModule,
    DropdownModule,
    InputTextModule,
    DynamicDialogModule,
    DialogModule,
    ConfirmDialogModule,
    ConfirmDialogModule,
    RippleModule,
    CheckboxModule,
    ConfirmDialogModule,
    SlideMenuModule,
    StepsModule,
    CardModule,
    PasswordModule,
    MenubarModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    ReactiveFormsModule,

    AngularEditorModule,
    HttpClientModule,

    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatFormFieldModule,
    AutoCompleteModule,
    ChipsModule,
    SelectButtonModule,
    MatButtonToggleModule,
    SplitterModule,

  ],
  providers: [
    {
      provide: "SocialAuthServiceConfig",
      useValue: {
        autoLogin: true,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              CLIENT_ID
            )
          }
        ]
      } as SocialAuthServiceConfig
    },
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent],
  // entryComponents: [PromotionModal]
})
export class AppModule { }