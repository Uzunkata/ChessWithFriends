import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PasswordResetService} from "./password-reset.service";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {
  private hash: string = "";
  password: string = "";
  passwordConfirm: string = "";

  constructor(private activatedRoute: ActivatedRoute, private passwordResetService: PasswordResetService, private router: Router) {
  }

  async sendPasswordReset(hash: string, password: string) {
    var a= await  this.passwordResetService.doSendPasswordReset(this.hash,this.password)
    console.log(a)
    this.password=""
    this.passwordConfirm=""
    this.router.navigateByUrl("/login");
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .subscribe(params => {
          this.hash = params['hash'];
          console.log(this.hash);
        }
      );
  }

  send() {
    if (this.password == this.passwordConfirm)
      this.sendPasswordReset(this.hash, this.password);
  }

}
