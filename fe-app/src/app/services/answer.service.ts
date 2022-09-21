import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Answer} from "../interfaces/material/answer.interface";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  url = '/api/answers'

  constructor(private http: HttpClient) {  }

  addAnswer(answerData: any): Observable<string> {
    return this.http.post<string>(`${this.url}/addAnswer`, answerData);
  }

  getAnswersByQuestion(questionId: number): Observable<Answer[]> {
    return this.http.get<Answer[]>(`${this.url}/question/${questionId}`)
  }

  upVoteAnswer(answerId: number): Observable<Answer> {
    return this.http.post<Answer>(`${this.url}/${answerId}/upvote`,null)
  }

  downVoteAnswer(answerId: number): Observable<Answer> {
    return this.http.post<Answer>(`${this.url}/${answerId}/downvote`,null)
  }

  deleteAnswer(answerId: number): Observable<Answer> {
    return this.http.delete<Answer>(`${this.url}/${answerId}/delete`)
  }

  editAnswer(answerData: any): Observable<string> {
    return this.http.post<string>(`${this.url}/edit`, answerData);
  }

  getAnswerById(answerId: number): Observable<Answer> {
    return this.http.post<Answer>(`${this.url}/${answerId}`, null)
  }

  setUsefulAnswer(answerData: any): Observable<Answer> {
    return this.http.post<Answer>(`${this.url}/selectAnswer`,answerData)
  }
  getMostUserfulAnswerByQuestionId(questionId: number): Observable<Answer> {
    return this.http.get<Answer>(`${this.url}/question/${questionId}/correct`)
  }


}
