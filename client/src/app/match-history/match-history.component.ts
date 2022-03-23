import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication-service/authentication.service';
import { MatchHisory } from '../model/MatchHistory';
import { MatchHistoryService } from './match-history.service';

@Component({
  selector: 'app-match-history',
  templateUrl: './match-history.component.html',
  styleUrls: ['./match-history.component.scss']
})
export class MatchHistoryComponent implements OnInit {

  username: any;
  matches: MatchHisory[];
  cols: any[];

  constructor(private matchHistoryService: MatchHistoryService, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.username = this.authenticationService.getUsername();
    this.matchHistoryService.getAllMatchesForUser(this.username).then(data => this.matches === undefined ? "" : data);

        this.cols = [
            { field: 'game_hash', header: 'Game hash'},
            { field: 'status', header: 'Status'},
            { field: 'player1', header: 'White'},
            { field: 'player2', header: 'Black'}
        ];
  }

}
