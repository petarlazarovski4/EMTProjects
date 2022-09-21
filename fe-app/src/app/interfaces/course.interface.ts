import {User} from "./user/User";

export interface Course {
  id: number;
  name: string;
  code: string;
  description: string;
  numMaterials?: number;
  moderators: User[];
}
