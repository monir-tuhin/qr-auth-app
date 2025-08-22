import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomerFeedbackDto} from '../_model/dto/customer-feedback-dto';
import {CustomerFeedbackService} from '../_services/customer-feedback.service';
import {ApiResponse} from '../_common/model/api-response';
import {ToastrService} from 'ngx-toastr';
import {QrCodeVerificationResponse} from '../_model/response/qr-code-verification-response';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.css']
})
export class VerifyComponent implements OnInit {
  customerFeedbackFg!: FormGroup;
  uid!: string;
  qrCodeVerificationResponse!: QrCodeVerificationResponse;
  isLoadingSpinner = false;
  isSaving = false;
  isSubmittedFeedback = false;

  constructor(private activatedRoute: ActivatedRoute,
              private cdr: ChangeDetectorRef,
              private formBuilder: FormBuilder,
              private toastrService: ToastrService,
              private customerFeedbackService: CustomerFeedbackService, ) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.uid = params?.uid;
      if (this.uid) {
        this.createCustomerFeedbackForm();
        this.qrCodeVerification(this.uid);
      }
    });
  }

  private createCustomerFeedbackForm(): void {
    this.customerFeedbackFg = this.formBuilder.group({
      id: [null],
      name: [],
      mobileNumber: [null, Validators.required],
      purchaseLocation: [],
      opinion: [],
      productUid: [this.uid, Validators.required],
    });
  }

  get f(): any {
    return this.customerFeedbackFg.controls;
  }

  submitCustomerFeedback(): void {
    this.isSaving = true;
    const formValue = this.customerFeedbackFg.value;
    const isValid = this.validateCustomerFeedbackForm(formValue);
    if (isValid) {
      const submitDto: CustomerFeedbackDto = new CustomerFeedbackDto(formValue);
      this.customerFeedbackService.save(submitDto).subscribe(
        (res: ApiResponse<string>) => {
          this.isSaving = false;
          this.isSubmittedFeedback = true;
          this.toastrService.success(res.message);
          this.resetCustomerFeedback();
        }, (err) => {
          this.isSaving = false;
          console.log(err);
        });
    }
  }

  resetCustomerFeedback(): void {
    this.customerFeedbackFg.reset();
  }

  private validateCustomerFeedbackForm(formValue: CustomerFeedbackDto): boolean {
    if (!formValue.mobileNumber) {
      this.toastrService.warning('Mobile no is required');
      return false;
    } else if (!formValue.productUid) {
      this.toastrService.warning('Product UID is required');
      return false;
    }
    return true;
  }

  qrCodeVerification(qrCode: string): void {
    this.isLoadingSpinner = true;
    this.customerFeedbackService.qrCodeVerification(qrCode).subscribe(
      (res: ApiResponse<QrCodeVerificationResponse>) => {
        this.isLoadingSpinner = false;
        this.qrCodeVerificationResponse = res.data;
      }, (err) => {
        this.isLoadingSpinner = false;
        this.toastrService.warning(err.error.message);
        console.log(err);
      });
  }

}
