import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication-service/authentication.service';
import { MatchHisory } from '../model/MatchHistory';
import { Status } from '../utils/Status';
import { MatchHistoryService } from './match-history.service';
// import { ChartType, ChartOptions } from 'chart.js';
// import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';

@Component({
  selector: 'app-match-history',
  templateUrl: './match-history.component.html',
  styleUrls: ['./match-history.component.scss']
})
export class MatchHistoryComponent implements OnInit {

  username: any;
  matches: MatchHisory[] = [];
  // cols: any[] = [];
  oponent: string = "";
  pipe = new DatePipe('en-US');


  constructor(private matchHistoryService: MatchHistoryService,
     private authenticationService: AuthenticationService,
     private router: Router) { }

  ngOnInit(): void {
    this.username = this.authenticationService.getUsername();
    this.matchHistoryService.getAllMatchesForUser(this.username)
      .then(data => this.matches = (data || []));

    // this.cols = [
    //   { field: 'gameHash', header: 'Game hash' },
    //   { field: 'status', header: 'Status' },
    //   { field: 'player1', header: 'White' },
    //   { field: 'player2', header: 'Black' },
    //   { field: 'winner', header: 'Winner' }
    // ];
  }

  // public pieChartOptions: ChartOptions = {
  //   responsive: true,
  // };
  // public pieChartLabels: Label[] = ['PHP', '.Net', 'Java'];
  // public pieChartData: SingleDataSet = [50, 30, 20];
  // public pieChartType: ChartType = 'pie';
  // public pieChartLegend = true;
  // public pieChartPlugins = [];

  getOponent(match: MatchHisory) {
    if (this.username == match.player1) {
      return match.player2;
    } else if (this.username == match.player2) {
      return match.player1;
    }
    return "";
  }

  isGameOver(match: MatchHisory) {
    if (match.status == Status.CHECKMATE) {
      return true;
    }
    return false;
  }

  isWon(match: MatchHisory) {
    if (match.winner == this.username) {
      return true;
    }
    return false;
  }

  findSide(match: MatchHisory) {
    if (match.player1 == this.username) {
      // return "White";
      return "assets/images/chess_peaces/whitePawn.svg.png";
    }else if(match.player2 == this.username){
      // return "Black";
      return "assets/images/chess_peaces/blackPawn.svg.png";
    }
    return "";
  }

  renderDate(date: Date) {
    console.log(this.pipe.transform(date, 'short')?.toString());
    return this.pipe.transform(date, 'short')?.toString();
  }

  loadGame(match: MatchHisory){
    let gameHash = match.gameHash;
    this.router.navigate(['/game/'+gameHash]).catch(console.error);
  }

}
