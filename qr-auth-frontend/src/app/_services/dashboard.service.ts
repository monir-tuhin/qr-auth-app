import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../environments/environment';
import {ApiResponse} from '../_common/model/api-response';
import {ToastrService} from 'ngx-toastr';
import {catchError, map, shareReplay} from 'rxjs/operators';
import {QrValidationSearchDto} from '../_model/dto/search/qr-validation-search-dto';
import {QrStatusSummaryResponse} from '../_model/response/qr-status-summary-response';


@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private readonly baseUrl = environment.baseUrl;

  constructor(private http: HttpClient,
              private toaster: ToastrService) { }


  getQrStatusSummary(searchDto: QrValidationSearchDto): Observable<Array<QrStatusSummaryResponse>> {
    const uri = `${this.baseUrl}/dashboard/qr-status-summary`;
    return this.http.post<ApiResponse<Array<QrStatusSummaryResponse>>>(uri, searchDto)
      .pipe(map(itm => itm.data), shareReplay(), catchError(err => {
        this.toaster.error(err.error.message);
        return throwError(err);
      }));
  }


}
