import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatchHisory } from '../model/MatchHistory';

@Injectable({
  providedIn: 'root'
})
export class MatchHistoryService {

  matchHistoryUrl = "http://localhost:4713/ochess/api/match-history";
  findAllMatchesForUserUrl = this.matchHistoryUrl + "/findAllForUser/?username=";

  constructor(private htttp: HttpClient) { }

  async getAllMatchesForUser(username: string){
    return await this.htttp.get<MatchHisory[]>(this.findAllMatchesForUserUrl+username).toPromise();
  }

}
