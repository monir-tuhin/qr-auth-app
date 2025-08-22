import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';
import {CustomerFeedbackDto} from '../_model/dto/customer-feedback-dto';
import {ApiResponse} from '../_common/model/api-response';
import {QrCodeVerificationResponse} from '../_model/response/qr-code-verification-response';


@Injectable({
  providedIn: 'root'
})
export class CustomerFeedbackService {

  private readonly baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  save(dto: CustomerFeedbackDto): Observable<ApiResponse<string>> {
    const uri = `${this.baseUrl}/public/customer-feedback`;
    return this.http.post<ApiResponse<string>>(uri, dto);
  }

  qrCodeVerification(qrCode: string): Observable<ApiResponse<QrCodeVerificationResponse>> {
    const uri = `${this.baseUrl}/public/qr-code-verification-scan`;
    const params = new HttpParams()
      .set('uid', qrCode);
    return this.http.put<ApiResponse<QrCodeVerificationResponse>>(uri, {}, {params});
  }

}
