import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import { AuthService } from 'src/app/_services/auth.service';
import {ApiResponse} from '../../_common/model/api-response';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-forgot-password-dialog',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordFg: FormGroup;
  isSubmitted = false;
  isLoading = false;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toaster: ToastrService){

    this.resetPasswordFg = new FormGroup({
      token: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required]),
    }, {validators: this.passwordMatchValidator});
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.resetPasswordFg.patchValue({token: params?.token ? params.token : ''});
    });
  }

  get password(): AbstractControl | null {
    return this.resetPasswordFg.get('password');
  }
  get confirmPassword(): AbstractControl | null {
    return this.resetPasswordFg.get('confirmPassword');
  }

  passwordMatchValidator(form: AbstractControl): ValidationErrors | null {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  get f(): any {
    return this.resetPasswordFg.controls;
  }

  resetPassword(): void {
    if (!this.resetPasswordFg.valid) {
      this.isSubmitted = true;
      return;
    }
    this.isLoading = true;
    this.authService.resetPassword(this.resetPasswordFg.value).subscribe(
      (res: ApiResponse<string>) => {
        this.toaster.success(res.message);
        this.isLoading = false;
        this.isSubmitted = false;
        this.router.navigate(['/login']);
      }, (err) => {
        console.log(err);
        this.isLoading = false;
        this.isSubmitted = false;
        this.toaster.error(err.error.message);
      });
  }

}
