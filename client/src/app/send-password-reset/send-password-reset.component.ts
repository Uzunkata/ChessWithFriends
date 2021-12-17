import { Component, OnInit } from '@angular/core';

import {SendPasswordResetService} from "./send-password-reset.service";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppComponent} from "../app.component";
import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'app-send-password-reset',
  templateUrl: './send-password-reset.component.html',
  styleUrls: ['./send-password-reset.component.scss'],
  providers: [MessageService]
})
export class SendPasswordResetComponent implements OnInit {
  email: string = "";
  hidden = false;

  constructor(private router: Router, private messageService: MessageService, private sendPasswordReset: SendPasswordResetService, private appCmp: AppComponent) {

  }

  ngOnInit(): void {

  }

  async sendPasswordResetRequest() {

    if(this.validateEmail(this.email)){

      try {
          await this.sendPasswordReset.doSendPasswordReset(this.email);
          this.hidden = true;
          this.appCmp.showToast("Email Sent!", "", false);
          this.reddirectLogin();
      } catch (e) {
      //     let msg = e.error;
      //     if (e.error.toString() == "[object ProgressEvent]") {
      //         msg = "Server timed out"
      //     } else
      //         msg = e.error;
      //     this.appCmp.showToast("Email couldn't be sent!", msg, true)
      }
    }

  }

validateEmail (emailAdress:String){
  let regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  if (emailAdress.match(regexEmail)) {
    return true; 
  } else {
    return false; 
  }
}

  reddirectLogin() {
      this.router.navigate(['/login'])
  }

  reddirectRegister() {
      this.router.navigate(['/register'])
  }
}
