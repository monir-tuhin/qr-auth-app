import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {GenerateQrCodeService} from '../_services/generate-qr-code.service';
import {QrVerificationHistoryResponse} from '../_model/response/qr-verification-history-response';
import {QrVerificationHistorySearchDto} from '../_model/dto/search/qr-verification-history-search-dto';
import {Page} from '../_common/model/page';
import {PageChangedEvent} from 'ngx-bootstrap';

@Component({
  selector: 'app-qr-verification-history',
  templateUrl: './qr-verification-history-component.html',
  styleUrls: ['./qr-verification-history-component.css']
})
export class QrVerificationHistoryComponent implements OnInit {
  searchFg!: FormGroup;
  page: Page<QrVerificationHistoryResponse> = new Page<QrVerificationHistoryResponse>();
  isLoadingPage!: boolean;
  isPrintQrCodeSummary!: boolean;

  constructor(private activatedRoute: ActivatedRoute,
              private cdr: ChangeDetectorRef,
              private formBuilder: FormBuilder,
              private generateQrCodeService: GenerateQrCodeService,
              private toastrService: ToastrService, ) { }

  ngOnInit(): void {
    this.createSearchForm();
    this.onSearch({offset: 0});
  }

  createSearchForm(): void {
    this.searchFg = new FormGroup({
      verificationStatusEnum: new FormControl(''),
      verificationTypeEnum: new FormControl(''),
      fromDate: new FormControl(''),
      toDate: new FormControl(''),
      searchTerm: new FormControl(''),
    });
  }

  historySearchPage(): void {
    const searchDto: QrVerificationHistorySearchDto = this.searchCriteria();
    this.isLoadingPage = true;
    this.generateQrCodeService.historySearchPage(searchDto).subscribe((response: Page<QrVerificationHistoryResponse>) => {
      this.isLoadingPage = false;
      this.page = response;
      this.page.begSerial = this.page.content.length > 0 ? (this.page.number * this.page.size) + 1 : 0;
      this.page.endSerial = (this.page.number * this.page.size) + this.page.content.length;
      this.searchFg.patchValue({loadingMode: false});
    }, err => {
      console.log(err);
      this.isLoadingPage = false;
      this.searchFg.patchValue({loadingMode: false});
      this.toastrService.error(err.error.message);
    });
  }

  searchCriteria(): QrVerificationHistorySearchDto {
    const searchDto = new QrVerificationHistorySearchDto();
    const formValue = this.searchFg.value;
    searchDto.uid = formValue.uid;
    // searchDto.verificationStatusEnum = formValue.verificationStatusEnum;
    searchDto.searchTerm = formValue.searchTerm;
    searchDto.page = this.page.pageable.pageNumber;
    searchDto.size = 10;
    return searchDto;
  }

  onSearch(pageInfo: any): void {
    this.page.pageable.pageNumber = pageInfo.offset;
    this.historySearchPage();
  }

  onResetSearch(): void {
    this.searchFg.reset();
    this.onSearch({offset: 0});
  }

  onPageChanged(event: PageChangedEvent): void {
    this.onSearch({offset: event.page - 1});
  }

  printQrCodeSummary(response: QrVerificationHistoryResponse): void {
    const searchDto = new QrVerificationHistorySearchDto();
    searchDto.uid = response.uid;
    response.isLoading = true;
    this.generateQrCodeService.printQrCodeSummary(searchDto).subscribe(res => {
      const file = new Blob([res], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      response.isLoading = false;
      this.cdr.markForCheck();
      window.open(fileURL, '_blank');
    }, (err) => {
      console.log(err);
      response.isLoading = false;
      this.toastrService.error(err.error.message);
    });
  }

}
