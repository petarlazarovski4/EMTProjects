import {ItemType} from "./item-type.enum";
import {Question} from "./question.interface";
import {SmxFile} from "./smx-file.interface";

export interface Item {
  // id: number;
  // type: ItemType;
  // question: Question;
  // file: SmxFile;
  name: string;
  question: string;
  url: string;
  type: ItemType;
  timePosted: Date;
  itemID: number;
  questionID: number;
}
