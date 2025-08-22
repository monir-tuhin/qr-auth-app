import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {ToastrService} from 'ngx-toastr';
import {ForgotPasswordDialogComponent} from './forgot-password-dialog/forgot-password-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  isLoading = false;
  isPasswordVisible = false;
  rememberMe = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService,
              private router: Router,
              private dialog: MatDialog,
              private toaster: ToastrService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
    const savedUser = localStorage.getItem('userData');
    if (savedUser) {
      this.toLoginForm(savedUser);
    }
  }

  toLoginForm(localStorageObj: string): void {
    const userData = JSON.parse(localStorageObj);
    this.form.username = userData.username;
    this.form.password = userData.password;
    this.rememberMe = true;
  }

  onSubmit(): void {
    this.isLoading = true;
    const { username, password } = this.form;
    this.authService.login(username, password).subscribe(
      data => {
        this.toaster.success('Successfully logged in!');
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);
        this.storeRememberMe(username, password);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.isLoading = false;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        this.isLoading = false;
      }
    );
  }

  storeRememberMe(username: string, password: string): void {
    if (this.rememberMe) {
      localStorage.setItem('userData', JSON.stringify({ username, password }));
    } else {
      localStorage.removeItem('userData');
    }
  }

  reloadPage(): void {
    this.router.navigate(['/home']);
    setTimeout(() => {
      window.location.reload();
    }, 50);
  }

  openForgotPasswordDialog(): void {
      const dialogRef = this.dialog.open(ForgotPasswordDialogComponent, {
        width: '400px',
        panelClass: 'mat-branded-modal',
      });
      dialogRef.afterClosed().subscribe({
        next: result => {
          console.log(result);
        },
        error: err => {
          console.log(err);
        }
      });
  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

}
