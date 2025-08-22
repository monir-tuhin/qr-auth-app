import { Component, OnInit } from '@angular/core';
import {DashboardService} from '../_services/dashboard.service';
import {QrValidationSearchDto} from '../_model/dto/search/qr-validation-search-dto';
import {ToastrService} from 'ngx-toastr';
import {QrStatusSummaryResponse} from '../_model/response/qr-status-summary-response';
import {QuarterEnum} from '../_model/enums/quarter-enum';
import {ChartTypeEnum} from '../_model/enums/chart-type-enum';
import {FormControl} from '@angular/forms';
import {UtilService} from '../_common/utils/util.service';
import {DashboardTypeEnum} from '../_model/enums/dashboard-type-enum';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  monthName = new Date().toLocaleString('default', { month: 'long' });

  sQdQuarter = new FormControl('Q1');
  sQdYear = new FormControl(new Date().getFullYear());
  sMdMonth = new FormControl(this.monthName);
  sMdYear = new FormControl(new Date().getFullYear());

  mQdQuarter = new FormControl('Q1');
  mQdYear = new FormControl(new Date().getFullYear());
  mMdMonth = new FormControl(this.monthName);
  mMdYear = new FormControl(new Date().getFullYear());

  view: [number, number] = [500, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = false;
  showXAxisLabel = true;
  showYAxisLabel = true;
  showDataLabel = true;
  xAxisLabel = 'Status';
  yAxisLabel = 'Count';

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  qrStatusSummaryQuarterly: QrStatusSummaryResponse[] = [];
  qrStatusSummaryMonthly: QrStatusSummaryResponse[] = [];
  qrMethodsSummaryQuarterly: QrStatusSummaryResponse[] = [];
  qrMethodsSummaryMonthly: QrStatusSummaryResponse[] = [];
  quarterEnumList: Array<QuarterEnum> = QuarterEnum.quarterEnumList;
  monthList: Array<string> = UtilService.getMonths();

  constructor(private dashboardService: DashboardService,
              private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.searchQrStatusQuarterlyDashboard();
    this.searchQrStatusMonthlyDashboard();
    this.searchQrMethodsQuarterlyDashboard();
    this.searchQrMethodsMonthlyDashboard();
  }

  searchQrStatusQuarterlyDashboard(): void {
    const searchDto = new QrValidationSearchDto();
    searchDto.quarterEnum = this.sQdQuarter.value;
    searchDto.year = this.sQdYear.value;
    searchDto.chartTypeEnum = ChartTypeEnum.QUARTER.enumKey;
    searchDto.dashboardTypeEnum = DashboardTypeEnum.STATUS.enumKey;
    this.dashboardService.getQrStatusSummary(searchDto).subscribe((response: Array<QrStatusSummaryResponse>) => {
      this.qrStatusSummaryQuarterly = response;
    }, err => {
      console.log(err);
      this.toastrService.error(err.error.message);
    });
  }

  searchQrStatusMonthlyDashboard(): void {
    const searchDto = new QrValidationSearchDto();
    searchDto.month = this.sMdMonth.value;
    searchDto.year = this.sMdYear.value;
    searchDto.chartTypeEnum = ChartTypeEnum.MONTH.enumKey;
    searchDto.dashboardTypeEnum = DashboardTypeEnum.STATUS.enumKey;
    this.dashboardService.getQrStatusSummary(searchDto).subscribe((response: Array<QrStatusSummaryResponse>) => {
      this.qrStatusSummaryMonthly = response;
    }, err => {
      console.log(err);
      this.toastrService.error(err.error.message);
    });
  }

  searchQrMethodsQuarterlyDashboard(): void {
    const searchDto = new QrValidationSearchDto();
    searchDto.quarterEnum = this.mQdQuarter.value;
    searchDto.year = this.mQdYear.value;
    searchDto.chartTypeEnum = ChartTypeEnum.QUARTER.enumKey;
    searchDto.dashboardTypeEnum = DashboardTypeEnum.METHOD.enumKey;
    this.dashboardService.getQrStatusSummary(searchDto).subscribe((response: Array<QrStatusSummaryResponse>) => {
      this.qrMethodsSummaryQuarterly = response;
    }, err => {
      console.log(err);
      this.toastrService.error(err.error.message);
    });
  }

  searchQrMethodsMonthlyDashboard(): void {
    const searchDto = new QrValidationSearchDto();
    searchDto.month = this.mMdMonth.value;
    searchDto.year = this.mMdYear.value;
    searchDto.chartTypeEnum = ChartTypeEnum.MONTH.enumKey;
    searchDto.dashboardTypeEnum = DashboardTypeEnum.METHOD.enumKey;
    this.dashboardService.getQrStatusSummary(searchDto).subscribe((response: Array<QrStatusSummaryResponse>) => {
      this.qrMethodsSummaryMonthly = response;
    }, err => {
      console.log(err);
      this.toastrService.error(err.error.message);
    });
  }

}
