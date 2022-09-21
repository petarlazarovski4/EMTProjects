import {Component, Input, OnInit} from '@angular/core';
import {Comment} from "../../../interfaces/material/comment.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {CommentService} from "../../../services/comment.service";
import {NotifierService} from "angular-notifier";
import {Observable} from "rxjs";
import {shareReplay} from "rxjs/operators";

@Component({
  selector: 'comment-wrapper',
  templateUrl: './comment-wrapper.view.html',
  styleUrls: ['./comment-wrapper.view.scss']
})
export class CommentWrapperView implements OnInit {

  @Input() materialId: number;
  commentForm: FormGroup;
  comments$: Observable<Comment[]>;
  comments: Comment[];

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private service: CommentService,
    private notifierService: NotifierService) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      description: ['', [Validators.required]]
    });
    this.comments$ = this.service.getAllCommentsByMaterialId(this.materialId);
    this.loadComments();
  }

  get f() {
    return this.commentForm.controls;
  }

  loadComments() {
    this.comments$.pipe(shareReplay(1)).subscribe(el => this.comments = el);
  }

  submit() {
    //submit comment
    let commentData = this.commentForm.value;
    commentData.materialId = this.materialId;
    this.service.addNewComment(commentData).subscribe(el => {
      this.notifierService.notify('success', 'Successfully added new comment.')
      location.reload();
    }, error => {
      this.notifierService.notify('error', 'Error adding new comment.')
    })
  }
}
