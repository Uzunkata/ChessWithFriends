import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatchHisory } from '../model/MatchHistory';

@Injectable({
  providedIn: 'root'
})
export class MatchHistoryService {

  matchHistoryUrl = "http://localhost:1440/ochess/api/match-history";
  findAllMatchesForUserUrl = this.matchHistoryUrl + "/findAllForUser/?username=";

  constructor(private htttp: HttpClient) { }

  getAllMatchesForUser(username: string){
    return this.htttp.get<MatchHisory[]>(this.findAllMatchesForUserUrl+username).toPromise();
  }

}
