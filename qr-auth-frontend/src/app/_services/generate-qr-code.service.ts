import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../environments/environment';
import {ApiResponse} from '../_common/model/api-response';
import {QrCodeSummaryResponse} from '../_model/response/qr-code-summary-response';
import {QrCodePdfRequest} from '../_model/request/qr-code-pdf-request';
import {QrVerificationHistorySearchDto} from '../_model/dto/search/qr-verification-history-search-dto';
import {QrVerificationHistoryResponse} from '../_model/response/qr-verification-history-response';
import { Page } from '../_common/model/page';
import {catchError, map, shareReplay} from 'rxjs/operators';
import {ToastrService} from 'ngx-toastr';
import {BrandEnum} from '../_model/enums/brand-enum';


@Injectable({
  providedIn: 'root'
})
export class GenerateQrCodeService {

  private readonly baseUrl = environment.baseUrl;

  constructor(private http: HttpClient,
              private toaster: ToastrService) { }

  generateQrCode(count: number, brandKey: string): Observable<ApiResponse<string>> {
    const uri = `${this.baseUrl}/generate-qr-code`;
    const params = new HttpParams()
      .set('count', count.toString())
      .set('brandKey', brandKey.toString());
    return this.http.post<ApiResponse<string>>(uri, {}, {params});
  }

  getGeneratedQrCodeSummary(): Observable<ApiResponse<QrCodeSummaryResponse>> {
    const uri = `${this.baseUrl}/generated-qr-code-summary`;
    return this.http.get<ApiResponse<QrCodeSummaryResponse>>(uri);
  }

  printQrCode(reqBody: QrCodePdfRequest): Observable<any> {
    const uri = `${this.baseUrl}/print-qr-code`;
    const httpOptions = {'responseType': 'arraybuffer' as 'json'};
    return this.http.post<any>(uri, reqBody, httpOptions);
  }

  historySearchPage(searchDto: QrVerificationHistorySearchDto): Observable<Page<QrVerificationHistoryResponse>> {
    const url = `${this.baseUrl}/qr-verification-history-search-page`;
    return this.http.post<ApiResponse<Page<QrVerificationHistoryResponse>>>(url, searchDto)
      .pipe(map(itm => itm.data), shareReplay(), catchError(err => {
        this.toaster.error(err.error.message);
        return throwError(err);
      }));
  }

  printQrCodeSummary(searchDto: QrVerificationHistorySearchDto): Observable<any> {
    const uri = `${this.baseUrl}/print-qr-code-summary`;
    const httpOptions = {'responseType': 'arraybuffer' as 'json'};
    return this.http.post<any>(uri, searchDto, httpOptions);
  }



}
