import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {catchError} from 'rxjs/operators/';
import { RestErrorInfo } from '../_models/rest.error.info';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:9082/usermanagement/api/users';

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  createUser(user: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, user).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, value).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  getUsersList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
      errorMessage = 'Error: ${error.error.message}';
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      if(error.status >= 400 && error.status <= 500) {
        console.error(
          `Backend returned code ${error.status}, ` +
          `body was: ${JSON.stringify(error.error)}`);

        let jsonObj: any = JSON.parse(JSON.stringify(error.error)); // string to generic object first
        let errorInfo: RestErrorInfo = <RestErrorInfo>jsonObj;
        errorMessage = errorInfo.errorMessages.toString();
      } else {
        errorMessage = 'Something wrong. Please try after sometime!!'
      }

    }
    // Return an observable with a user-facing error message.
    return throwError(
      errorMessage);
  }
}
