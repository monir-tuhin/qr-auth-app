import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ApiResponse} from '../_common/model/api-response';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {SignupRequest} from '../_model/request/signup-request';
import {RoleEnum} from '../_model/enums/role-enum';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  signupFg!: FormGroup;

  isSuccessful = false;
  isSignUpFailed = false;

  isPasswordVisible = false;
  isConfirmPasswordVisible = false;
  isSubmitted = false;
  isLoading = false;
  errorMessage = '';

  constructor(private authService: AuthService,
              private toaster: ToastrService,
              private router: Router, ) {
    this.createSignupForm();
  }

  ngOnInit(): void {
  }

  createSignupForm(): void {
    this.signupFg = new FormGroup({
      fullName: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/)
      ]),
      mobileNumber: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required]),
    }, {validators: this.passwordMatchValidator});
  }

  passwordMatchValidator(form: AbstractControl): ValidationErrors | null {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  get f(): any {
    return this.signupFg.controls;
  }

  onSubmit(): void {
    if (!this.signupFg.valid) {
      this.isSubmitted = true;
      return;
    }
    const signupRequest: SignupRequest = new SignupRequest(this.signupFg.value);
    signupRequest.roles = [RoleEnum.ROLE_USER];

    this.isLoading = true;
    this.authService.signup(signupRequest).subscribe(
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

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }
  toggleConfirmPasswordVisibility(): void {
    this.isConfirmPasswordVisible = !this.isConfirmPasswordVisible;
  }

  reset(): void {
    this.signupFg.reset();
  }

}
