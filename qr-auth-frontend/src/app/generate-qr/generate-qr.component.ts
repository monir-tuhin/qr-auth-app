import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {ApiResponse} from '../_common/model/api-response';
import {GenerateQrCodeService} from '../_services/generate-qr-code.service';
import {QrCodeSummaryResponse} from '../_model/response/qr-code-summary-response';
import {QrCodePdfRequest} from '../_model/request/qr-code-pdf-request';
import {BrandEnum} from '../_model/enums/brand-enum';

@Component({
  selector: 'app-generate-qr',
  templateUrl: './generate-qr.component.html',
  styleUrls: ['./generate-qr.component.css']
})
export class GenerateQrComponent implements OnInit {
  brand: FormControl = new FormControl('');
  count: FormControl = new FormControl();
  countFrom: FormControl = new FormControl();
  countTo: FormControl = new FormControl();
  isGenerated = false;
  isSummary = false;
  isPrintQrCode = false;
  qrCodeSummaryResponse!: QrCodeSummaryResponse;
  brandEnumList: Array<BrandEnum> = BrandEnum.brandEnumList;

  constructor(private activatedRoute: ActivatedRoute,
              private cdr: ChangeDetectorRef,
              private formBuilder: FormBuilder,
              private generateQrCodeService: GenerateQrCodeService,
              private toastrService: ToastrService, ) { }

  ngOnInit(): void {
    this.generatedQrCodeSummary();
  }

  generateQrCode(): any {
    if (!this.brand.value) {
      return this.toastrService.warning('Please select a brand.');
    }
    if (this.count.value > 100000) {
     return this.toastrService.warning('You can not generate more than 100000 QR code at a time', 'Warning');
    }
    this.isGenerated = true;
    this.generateQrCodeService.generateQrCode(this.count.value, this.brand.value).subscribe(
      (res: ApiResponse<string>) => {
        this.count.reset();
        this.isGenerated = false;
        this.toastrService.success(res.message);
        this.generatedQrCodeSummary();
      }, (err) => {
        this.count.reset();
        this.isGenerated = false;
        this.toastrService.error(err.error.message);
        console.log(err);
      });
  }

  generatedQrCodeSummary(): void {
    this.isSummary = true;
    this.generateQrCodeService.getGeneratedQrCodeSummary().subscribe(
      (res: ApiResponse<QrCodeSummaryResponse>) => {
        this.isSummary = false;
        this.qrCodeSummaryResponse = res.data;
      }, (err) => {
        this.isSummary = false;
        this.toastrService.error(err.error.message);
        console.log(err);
      });
  }

  printQrCode(): any {
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
  }


}
