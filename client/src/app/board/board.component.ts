import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/User';
import { $WebSocket } from '../web-socket/websocket.service';
import { AuthenticationService } from '../authentication/authentication.service';
import { Status } from '../utils/Status';
import { Color } from '../utils/Color';
// import { Overlay, overlayConfigFactory } from 'angular2-modal';
// import { Modal, BSModalContext } from 'angular2-modal/plugins/bootstrap';
// import { PromotionModalContext, PromotionModal } from '../promotion-modal';


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {
  private ws: $WebSocket;
  public boardSize: number = 0;
  public squareSize: number = 0;
  public fontSize: number = 0;
  private gameUUID: string = '';
  private requestUUID: string = '';
  private sub: any;
  private movement: Movement;
  private myColor: Color;
  private user: User = {} as User;
  // private myPlayerUUID: string = '';
  private lastStatus: number;
  game: any;

  token: any;
  isLogedin = false;
  username: string;

  promotionDialog: boolean;
  alertDialog: boolean;
  promotionDialogPosition: string;

  title: string;
  body: string;
  pieceToPromote: any;

  constructor(private route: ActivatedRoute, private router: Router,
      //overlay: Overlay,
      vcRef: ViewContainerRef,
      //public modal: Modal
      authenticationService: AuthenticationService
      ) {
    this.movement = { position1: { x: null, y: null }, position2: { x: null, y: null } };
    //overlay.defaultViewContainer = vcRef;
    this.isLogedin = authenticationService.checkLogin();
    this.token = window.localStorage.getItem("access_token");
    this.username = authenticationService.getUsername(this.token);
    localStorage.setItem("myPlayerUsername", this.username);
  }

  ngOnInit() {
    this.game = {};
    this.game.board = {};
    this.game.board.rows = [];
    this.sub = this.route.params.subscribe(params => {
      this.game.board.rows = [];
      this.gameUUID = params["gameUUID"];
      // if (localStorage.getItem("myPlayerUUID" + params["gameUUID"])) {
      //   // this.myPlayerUUID = localStorage.getItem("myPlayerUUID" + params["gameUUID"]) || '';
      // }
      if(!localStorage.getItem("gameUUID")){
        localStorage.setItem("gameUUID", this.gameUUID);
      }
      if (localStorage.getItem("myPlayerUsername")) {
      this.username = localStorage.getItem("myPlayerUsername") || "";
      }
      this.ws = new $WebSocket();
      this.subscribeToWebSocket();
      this.requestJoinGame();
      this.requestUpdate();
    });
  }

  click(x: any, y: any) {
    this.resetColors();
    if (this.game.status == Status.CHECKMATE) {
      return;
    }
    if (this.game.board.rows[y].squares[x].piece &&
      this.game.board.rows[y].squares[x].piece.color == this.myColor) {
      this.startMovement(x, y);
    } else if (this.movement.position1.x != null) {
      this.completeMovement(x, y);
    }
  }

  startMovement(x: number, y: number) {
    if (this.game.turnColor != this.myColor) {
      return;
    }
    this.game.board.rows[y].squares[x].border = "5px solid lightgreen";
    let movement = { position1: { x: x, y: y }, position2: { x: null, y: null } };
    if (this.myColor == Color.BLACK) {
      movement = { position1: { x: 7 - x, y: 7 - y }, position2: { x: null, y: null } }
    }
    let message: Message = {
      action: 'requestPossibleMovements',
      movement: movement,
      gameUUID: this.gameUUID,
      username: this.username,
      // playerUUID: this.myPlayerUUID,
      requestUUID: null,
      promoteTo: null
    }
    this.ws.send(message);
    this.movement = movement;
  }

  completeMovement(x: number, y: number) {
    this.movement.position2.x = x;
    this.movement.position2.y = y;
    if (this.myColor == Color.BLACK) {
      this.movement.position2.x = 7 - x;
      this.movement.position2.y = 7 - y;
    }
    let message: Message = {
      action: 'move',
      movement: this.movement,
      gameUUID: this.gameUUID,
      username: this.username,
      // playerUUID: this.myPlayerUUID,
      requestUUID: null,
      promoteTo: null
    }
    this.ws.send(message);
    this.movement = { position1: { x: null, y: null }, position2: { x: null, y: null } };
  }

  subscribeToWebSocket() {
    this.ws.getDataStream().subscribe(
      (res: any) => {
        if (JSON.parse(res.data).type == "possibleMovements") {
          let availableMovements = JSON.parse(res.data).possibleMovements;
          this.processAvailableMovements(availableMovements);
        } else if (JSON.parse(res.data).type == "joinGame") {
          if (this.requestUUID == JSON.parse(res.data).requestUUID) {
            let assignedPlayer = JSON.parse(res.data).assignedPlayer;
            this.joinGame(assignedPlayer);
            this.updateBoard(this.game);
          }
        } else if (JSON.parse(res.data).type == "updateBoard") {
          let game = JSON.parse(res.data).game;
          this.updateBoard(game);
        }
      },
      function (e: any) { console.log('Error: ' + e.message); },
      function () { console.log('Completed'); }
    );
  }

  requestUpdate() {
    let message: Message = {
      action: 'requestUpdate',
      movement: null,
      gameUUID: this.gameUUID,
      username: this.username,
      // playerUUID: this.myPlayerUUID,
      requestUUID: null,
      promoteTo: null
    }
    this.ws.send(message);
  }

  requestJoinGame() {
    this.requestUUID = this.generateUUID();
    let message: Message = {
      action: 'joinGame',
      username: this.username,
      // playerUUID: this.myPlayerUUID,
      movement: null,
      gameUUID: this.gameUUID,
      requestUUID: this.requestUUID,
      promoteTo: null
    }
    this.ws.send(message);
  }

  joinGame(player: any) {
    if (player != null) {
      // this.myPlayerUUID = player.uuid;
      this.myColor = player.color;
      this.username = player.username;
      // localStorage.setItem("myPlayerUUID" + this.gameUUID, player.uuid);
      localStorage.setItem("myPlayerUsername", this.username);
    }
  }

  generateUUID() {
    let d = new Date().getTime();
    if (window.performance && typeof window.performance.now === "function") {
      d += performance.now(); //use high-precision timer if available
    }
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
      let r = (d + Math.random() * 16) % 16 | 0;
      d = Math.floor(d / 16);
      return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
  }

  processAvailableMovements(availableMovements: any) {
    if (this.game.turnColor != this.myColor) {
      return;
    }
    for (let movement of availableMovements) {
      if (this.myColor == Color.BLACK) {
        movement.position2.x = 7 - movement.position2.x;
        movement.position2.y = 7 - movement.position2.y;
      }
      this.game.board.rows[movement.position2.y].squares[movement.position2.x].border = "5px solid lightgreen";
    }
  }

  updateBoard(game: any) {
    if (game.status == Status.CHECK &&
      game.status != this.lastStatus &&
      this.myColor != Color.SPECTATOR &&
      this.myColor == game.turnColor) {
      // this.modal.alert()
      //   .title('CHECK')
      //   .body('You are in CHECK.')
      //   .open();
      this.alertDialog = true;
      this.title = 'CHECK';
      this.body = 'You are in CHECK.';
    }
    if (game.status == Status.CHECKMATE &&
      game.status != this.lastStatus &&
      this.myColor != Color.SPECTATOR &&
      this.myColor == game.turnColor) {
      // this.modal.alert()
      //   .title('CHECKMATE')
      //   .body('You lost.')
      //   .open();
      this.alertDialog = true;
      this.title = 'CHECKMATE';
      this.body = 'You lost.';
    }
    if (game.status == Status.CHECKMATE &&
      game.status != this.lastStatus &&
      this.myColor != Color.SPECTATOR &&
      this.myColor != game.turnColor) {
      // this.modal.alert()
      //   .title('CHECKMATE')
      //   .body('You won.')
      //   .open();
      this.alertDialog = true;
      this.title = 'CHECKMATE';
      this.body = 'You won.';
    }
    if (game.isPromotion && game.turnColor == this.myColor) {
      // this.modal.open(PromotionModal, overlayConfigFactory({ num1: 2, num2: 3 }, BSModalContext))
      //   .then(dialog => dialog.result)
      //   .then(result => this.doPromote(result))
      //   .catch(err => this.doPromote("Queen"));
      this.promotionDialogPosition = 'top';
      this.promotionDialog = true;
    }
    this.lastStatus = game.status;
    if (this.myColor == Color.BLACK) {
      game.board = this.invertBoard(game.board);
    }
    this.resize();
    this.game = game;
    this.playBeep();
  }

  // onKeyUp(value: any) {
  //   this.wrongAnswer = value != 5;
  //   // this.dialog.close();
  //   this.displayPosition =false;
  // }

  pickPiece(piece: string) {
    this.pieceToPromote = piece;
    this.doPromote(this.pieceToPromote);
    this.promotionDialog =false;
    // this.dialog.close(piece);
  }

  // beforeDismiss(): boolean {
  //   return true;
  // }

  // beforeClose(): boolean {
  //   return false;
  // }


  doPromote(piece: string) {
    let message: Message = {
      action: 'doPromote',
      movement: null,
      gameUUID: this.gameUUID,
      username: this.username,
      // playerUUID: this.myPlayerUUID,
      requestUUID: null,
      promoteTo: piece
    }
    this.ws.send(message);
  }

  playBeep() {
    // let audio = new Audio();
    // audio.src = "http://104.131.146.200/chess/chessmove.mp3";
    // audio.load();
    // audio.play();
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.resize();
    }, 1);
  }

  resize() {
    this.boardSize = Math.min(window.innerHeight - 40, window.innerWidth);
    this.squareSize = this.boardSize / 8;
    this.fontSize = Math.floor(100 * this.squareSize / 16 / 1.8) / 100;
  }

  invertBoard(board: any) {
    board.rows = board.rows.slice().reverse();
    for (let row of board.rows) {
      row.squares = row.squares.slice().reverse();
    }
    return board;
  }

  resetColors() {
    for (let x = 0; x <= 7; x++) {
      for (let y = 0; y <= 7; y++) {
        this.game.board.rows[y].squares[x].border = null;
      }
    }
  }

}


interface Message {
  action: string;
  movement: Movement | null;
  gameUUID: string;
  username: String;
  // playerUUID: string;
  requestUUID: string | null;
  promoteTo: string | null;
}

interface Movement {
  position1: Position;
  position2: Position;
}

interface Position {
  x: number | null;
  y: number | null;
}
