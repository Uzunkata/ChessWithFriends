import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmEmailService } from './confirm-email.service';

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.scss']
})
export class ConfirmEmailComponent implements OnInit {

  hash: string = "";
  confirmationMessage: string = "Waiting for confirmation!";

  constructor(private activatedRoute: ActivatedRoute, private confirmEmailService: ConfirmEmailService,  private router: Router) { }

  async confirmEmail() {
    console.log(await this.confirmEmailService.doConfrimEmail(this.hash, this.confirmationMessage));
    this.router.navigateByUrl('/login');

  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .subscribe(params => {
          this.hash = params['hash'];
          console.log(this.hash);
        }
      );
    this.confirmEmail()

  }
}