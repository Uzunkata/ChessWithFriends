import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication-service/authentication.service';
import { MatchHisory } from '../model/MatchHistory';
import { Status } from '../utils/Status';
import { MatchHistoryService } from './match-history.service';

@Component({
  selector: 'app-match-history',
  templateUrl: './match-history.component.html',
  styleUrls: ['./match-history.component.scss']
})
export class MatchHistoryComponent implements OnInit {

  username: any;
  matches: MatchHisory[] = [];
  cols: any[] = [];
  oponent: string = "";

  constructor(private matchHistoryService: MatchHistoryService, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.username = this.authenticationService.getUsername();
    this.matchHistoryService.getAllMatchesForUser(this.username)
      .then(data => this.matches = (data || []));

    this.cols = [
      { field: 'gameHash', header: 'Game hash' },
      { field: 'status', header: 'Status' },
      { field: 'player1', header: 'White' },
      { field: 'player2', header: 'Black' },
      { field: 'winner', header: 'Winner' }
    ];
  }

  getOponent(match: MatchHisory){
    if(this.username==match.player1){
      return match.player2;
    }else if(this.username==match.player2){
      return match.player1;
    }
    return "";
  }

  isGameOver(match: MatchHisory){
    if(match.status == Status.CHECKMATE){
      return true;
    }
    return false;
  }

  isWon(match: MatchHisory){
    if(match.winner==this.username){
      return true;
    }
    return false;
  }

}
