import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';
import {ApiResponse} from '../_common/model/api-response';
import {ForgotPasswordRequest} from '../_model/request/forgot-password-request';
import {SignupRequest} from '../_model/request/signup-request';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = environment.baseUrl;
  private readonly authUri = `${this.baseUrl}/auth/`;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(this.authUri + 'signin', {username, password}, httpOptions);
  }

  signup(request: SignupRequest): Observable<ApiResponse<string>> {
    const uri = `${this.baseUrl}/auth/signup`;
    return this.http.post<ApiResponse<string>>(uri, request);
  }

  forgotPassword(request: ForgotPasswordRequest): Observable<ApiResponse<string>> {
    const uri = `${this.baseUrl}/auth/forgot-password`;
    return this.http.post<ApiResponse<string>>(uri, request);
  }

  resetPassword(formValue: any): Observable<ApiResponse<string>> {
    const uri = `${this.baseUrl}/auth/reset-password`;
    const params = new HttpParams()
      .set('token', formValue?.token)
      .set('password', formValue?.password);
    return this.http.post<ApiResponse<string>>(uri, {}, {params});
  }

}
