import { Status } from "../utils/Status";

export interface MatchHisory{
    gameHash: string;
    player1: string;
    player2: string;
    status: Status;
    winner: string;
}