import {User} from "../user/User";
import {Comment} from "./comment.interface";
import {Item} from "./item.interface";

export interface Material {
  id: number;
  title: string;
  createdBy: User;
  dateCreated: Date;
  published: boolean;
  description: string;
  upVotes: number;
  downVotes: number;
  comments: Comment[];
  items: Item[];
}
