import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {AnswerService} from "../../../services/answer.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Answer} from "../../../interfaces/material/answer.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotifierService} from "angular-notifier";
import {User} from "../../../interfaces/user/User";
import {UserService} from "../../../services/user-auth/user.service";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";
import {shareReplay} from "rxjs/operators";

@Component({
  selector: 'answer-dialog',
  templateUrl: './answer-dialog.component.html',
  styleUrls: ['./answer-dialog.component.scss']
})
export class AnswerDialogComponent implements OnInit {

  answers$: Observable<Answer[]>
  correctAnswer: Answer;
  answerForm: FormGroup;
  currentUser: User;
  answers: Answer[];
  canSelectMostUsefulAnswer: boolean = false;
  constructor(private answerService: AnswerService,
              private builder: FormBuilder,
              private notifierService: NotifierService,
              private userService: UserService,
              private roleService: RoleAuthenticatorService,
              @Inject(MAT_DIALOG_DATA) public data: { questionId: number, postedBy: User }) {
  }

  ngOnInit(): void {
    this.answerForm = this.builder.group({
      answer: ['', Validators.required]
    });
    this.canSelectUsefulAnswer();
    this.answers$ = this.answerService.getAnswersByQuestion(this.data.questionId);
    this.loadAnswers();
    this.answerService.getMostUserfulAnswerByQuestionId(this.data.questionId).subscribe(el => {
      this.correctAnswer = el;
    });
    this.currentUser = this.userService.getCurrentUser();
  }

  get f() {
    return this.answerForm.controls;
  }

  onSubmit() {
    let data = this.answerForm.value;
    data.questionID = this.data.questionId;

    this.answerService.addAnswer(data).subscribe(el => {
      this.notifierService.notify('success', 'Answer saved.')
      this.answerForm.reset();
      this.loadAnswers();
    }, error => {
      this.notifierService.notify('error', 'Error saving answer.')
    })
  }

  isAnswerCorrect(answer: Answer) {
    return this.correctAnswer != null && this.correctAnswer.id == answer.id;
  }

  canSelectUsefulAnswer() {
    this.canSelectMostUsefulAnswer = this.roleService.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_MODERATOR, Role.ROLE_ADMIN]) || this.data.postedBy.id == this.currentUser.id;
  }

  loadAnswers() {
    this.answers$.pipe(
      shareReplay(1)
    ).subscribe(el => {
      this.answers = el;
    });
  }

  selectUsefulAnswer(answer: Answer) {
    if(!this.correctAnswer) {
      let data = {questionID: this.data.questionId, answerID: answer.id};
      this.answerService.setUsefulAnswer(data).subscribe(el => {
        this.notifierService.notify('success', 'Most useful answer selected.')
        this.loadAnswers();
      }, error => {
        this.notifierService.notify('error', 'Error setting most useful answer.')
      })
    } else {
      this.notifierService.notify('warning', 'There is already a useful answer set.')
    }
  }

  upVoteAnswer(answer: Answer) {
    this.answerService.upVoteAnswer(answer.id).subscribe(el => {
      this.notifierService.notify('success', 'Upvoted');
      this.loadAnswers();
    }, error => {
      this.notifierService.notify('error', error.error);
    })
  }

  downVoteAnswer(answer: Answer) {
    this.answerService.downVoteAnswer(answer.id).subscribe(el => {
      this.notifierService.notify('success', 'Downvoted');
      this.loadAnswers();
    }, error => {
      this.notifierService.notify('error', error.error);
    })
  }
}
