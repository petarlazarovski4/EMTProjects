import {User} from "../user/User";

export interface Answer {
  id: number;
  answer: string;
  upVotes: number;
  downVotes: number;
  answeredBy: User;
  time: Date;
}
