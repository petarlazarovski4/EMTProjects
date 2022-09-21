import {User} from "../user/User";

export interface Comment {
  id:number;
  description: string;
  upVotes: number;
  downVotes: number;
  createdBy: User;
  datePosted: Date;
}
