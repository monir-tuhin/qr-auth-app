import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {ReportsService} from '../_services/reports.service';
import { saveAs } from 'file-saver';
import {ValidationMethodsEnum} from '../_model/enums/validation-methods-enum';
import {ValidationStatusEnum} from '../_model/enums/validation-status-enum';
import {WeekEnum} from '../_model/enums/week-enum';
import {QuarterEnum} from '../_model/enums/quarter-enum';
import {UtilService} from '../_common/utils/util.service';
import {QrValidationSearchDto} from '../_model/dto/search/qr-validation-search-dto';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  searchFg!: FormGroup;
  validationMethodsEnumList: Array<ValidationMethodsEnum> = ValidationMethodsEnum.validationMethodsEnumList;
  validationStatusEnumList: Array<ValidationStatusEnum> = ValidationStatusEnum.validationStatusEnumList;
  weekEnumList: Array<WeekEnum> = WeekEnum.weekEnumList;
  quarterEnumList: Array<QuarterEnum> = QuarterEnum.quarterEnumList;
  monthList: Array<string> = UtilService.getMonths();
  isDownloading = false;

  constructor(private activatedRoute: ActivatedRoute,
              private cdr: ChangeDetectorRef,
              private fb: FormBuilder,
              private reportsService: ReportsService,
              private toastrService: ToastrService, ) {

  }

  ngOnInit(): void {
    this.initSearchForm();
  }

  initSearchForm(): void {
    this.searchFg = this.fb.group({
      uid: [''],
      validationMethodsEnum: [null],
      validationStatusEnum: [null],
      mobileNumber: [''],
      // validationDate: [''],
      weekEnum: [null],
      month: [null],
      quarterEnum: [null],
      year: ['']
    });
  }

  downloadExcel(): any {
    if (this.searchFg.value.year && (this.searchFg.value.year).toString().length !== 4) {
      return this.toastrService.warning('Year must have only 4 digit');
    }
    this.isDownloading = true;
    this.reportsService.downloadExcel(this.searchCriteria()).subscribe(
      (res: any) => {
        this.isDownloading = false;
        const fileName = 'qr_validation.xlsx';
        saveAs(res, fileName);
      }, (error) => {
        this.isDownloading = false;
        console.error('Error downloading Excel file:', error);
        this.toastrService.error('Error downloading Excel file:', error);
      });
  }

  searchCriteria(): QrValidationSearchDto {
    const searchDto = new QrValidationSearchDto();
    searchDto.uid = this.searchFg.value?.uid;
    searchDto.mobileNumber = this.searchFg.value?.mobileNumber;
    searchDto.month = this.convertValue(this.searchFg.value?.month);
    searchDto.validationMethodsEnum = this.convertValue(this.searchFg.value?.validationMethodsEnum);
    searchDto.validationStatusEnum = this.convertValue(this.searchFg.value?.validationStatusEnum);
    searchDto.weekEnum = this.convertValue(this.searchFg.value?.weekEnum);
    searchDto.quarterEnum = this.convertValue(this.searchFg.value?.quarterEnum);
    searchDto.year = this.searchFg.value?.year;
    return searchDto;
  }

  convertValue(value: any): any {
    if (value === undefined || value === '' || value === 'null' || value === 'undefined') {
      return null;
    }
    return value;
  }

  /*  printQrCode(): any {
    if (this.countFrom.value > this.countTo.value) {
      return this.toastrService.warning('Count from can not be more than Count to');
    }

    const diffCount = this.countTo.value - this.countFrom.value;
    if (diffCount > 5000) {
      return this.toastrService.warning('You can not print QR code more than 5000 at a time');
    }

    const reqBody: QrCodePdfRequest = new QrCodePdfRequest(
      {countFrom: this.countFrom.value, countTo: this.countTo.value}
    );
    this.isPrintQrCode = true;
    this.generateQrCodeService.printQrCode(reqBody).subscribe(res => {
      const file = new Blob([res], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      this.isPrintQrCode = false;
      this.cdr.markForCheck();
      window.open(fileURL, '_blank');
    }, (err) => {
      this.isPrintQrCode = false;
      this.toastrService.error(err.error.message);
      console.log(err);
    });
  }*/

  reset(): void {
    this.searchFg.reset();
  }
}
