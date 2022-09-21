import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {Item} from "../../../interfaces/material/item.interface";
import {ItemType} from "../../../interfaces/material/item-type.enum";
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AnswerDialogComponent} from "../answer-dialog/answer-dialog.component";
import {User} from "../../../interfaces/user/User";

@Component({
  selector: 'item-wrapper',
  templateUrl: './item-wrapper.view.html',
  styleUrls: ['./item-wrapper.view.scss']
})
export class ItemWrapperView  {

  @Input() items: Item[];
  @Input() materialPostedBy: User;
  itemTypes = ItemType;
  constructor(private dialog: MatDialog) { }

  downloadFile(item: Item) {
    window.open(item.url);
  }

  openDialog(questionId: number) {
    const dialogRef = this.dialog.open(AnswerDialogComponent, {
      data: {
        questionId: questionId,
        postedBy: this.materialPostedBy
      }
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

}

