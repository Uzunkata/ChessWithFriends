import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { $WebSocket } from '../web-socket/websocket.service';
// import { UUID } from 'angular2-uuid';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-matchmaking',
  templateUrl: './matchmaking.component.html',
  styleUrls: ['./matchmaking.component.scss']
})
export class MatchmakingComponent implements OnInit {
  ws: $WebSocket;

  constructor(private router:Router) { }

  ngOnInit() {
    localStorage.clear();
  }

  play() {
    this.ws = new $WebSocket();
    this.sendNewMatchRequest();
    this.waitNewMatchRespose();
  }

  sendNewMatchRequest() {
    let uuid = uuidv4();
    localStorage.setItem('gameUUID', uuid);
    // localStorage.setItem('myPlayerNumber', '0');
    let message:Message = {
      action: 'newGame',
      gameUUID: uuid
    }
    this.ws.send(message);
  }

  waitNewMatchRespose() {
    this.ws.getDataStream().subscribe(
      res => {
        let game = JSON.parse(res.data);
        if(game.uuid) {
          this.router.navigate(['/game/'+game.uuid]);
        }
      },
      function(e) { console.log('Error: ' + e.message); },
      function() { console.log('Completed'); }
    );
  }

}


interface Message {
  action: string;
  gameUUID: string;
}
