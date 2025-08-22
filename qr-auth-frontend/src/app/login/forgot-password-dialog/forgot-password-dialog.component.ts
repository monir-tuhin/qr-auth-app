import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { AuthService } from 'src/app/_services/auth.service';
import {ApiResponse} from '../../_common/model/api-response';
import {ForgotPasswordRequest} from '../../_model/request/forgot-password-request';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-forgot-password-dialog',
  templateUrl: './forgot-password-dialog.component.html',
  styleUrls: ['./forgot-password-dialog.component.css']
})
export class ForgotPasswordDialogComponent implements OnInit {
  forgotPasswordForm: FormGroup;
  isSending = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<ForgotPasswordDialogComponent>,
              private authService: AuthService,
              private toaster: ToastrService){

    this.forgotPasswordForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/)
      ])
    });

  }

  ngOnInit(): void {

  }

  get email(): FormControl {
    return this.forgotPasswordForm.get('email') as FormControl;
  }

  close(): void {
    this.dialogRef.close('');
  }

  sendEmailNotification(): void {
    if (!this.forgotPasswordForm.valid) {
      return;
    }
    this.isSending = true;
    const request = new ForgotPasswordRequest({email: this.email.value});
    this.authService.forgotPassword(request).subscribe(
      (res: ApiResponse<string>) => {
        this.isSending = false;
        this.toaster.success(res.message);
        this.close();
      }, (err) => {
        console.log(err);
        this.isSending = false;
        this.toaster.error(err.error.message);
      });
  }

}
