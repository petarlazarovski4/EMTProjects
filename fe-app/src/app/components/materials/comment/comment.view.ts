import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Comment} from "../../../interfaces/material/comment.interface";
import {CommentService} from "../../../services/comment.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'comment',
  templateUrl: './comment.view.html',
  styleUrls: ['./comment.view.scss']
})
export class CommentView implements OnInit {

  @Input() comment: Comment;
  constructor(private service: CommentService, private notifierService: NotifierService) { }
  @Output() refresh: EventEmitter<boolean> = new EventEmitter<boolean>();

  ngOnInit(): void {
  }

  refreshComments() {
    this.refresh.emit(true);
  }

  upvoteComment(comment) {
    this.service.upvoteComment(comment.id).subscribe(el => {
      this.notifierService.notify('success', 'Liked Comment');
      this.refreshComments()
    }, error => {
      this.notifierService.notify('error', error.error);
    });
  }

  downvoteComment(comment) {
    this.service.downvoteComment(comment.id).subscribe(el => {
      this.notifierService.notify('success', 'Downvoted Comment');
      this.refreshComments()
    }, error => {
      this.notifierService.notify('error', error.error);
    });
  }

}
