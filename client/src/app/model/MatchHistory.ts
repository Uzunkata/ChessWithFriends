import { Status } from "../utils/Status";
import { User } from "./User";

export interface MatchHisory{
    id?: number;
    gameHash?: String;
    player1?: User;
    player2?: User;
    status?: Status;
    winner?: User;
}