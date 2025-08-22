import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../environments/environment';
import {QrCodePdfRequest} from '../_model/request/qr-code-pdf-request';
import {ToastrService} from 'ngx-toastr';
import {QrValidationSearchDto} from '../_model/dto/search/qr-validation-search-dto';


@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  private readonly baseUrl = environment.baseUrl;

  constructor(private http: HttpClient,
              private toaster: ToastrService) { }

  printQrCode(reqBody: QrCodePdfRequest): Observable<any> {
    const uri = `${this.baseUrl}/print-qr-code`;
    const httpOptions = {'responseType': 'arraybuffer' as 'json'};
    return this.http.post<any>(uri, reqBody, httpOptions);
  }

  downloadExcel(searchDto: QrValidationSearchDto): Observable<any> {
    const uri = `${this.baseUrl}/qr-validation/download-excel`;
    const httpOptions = {'responseType': 'blob' as 'json'};
    return this.http.post<any>(uri, searchDto, httpOptions);
  }


}
