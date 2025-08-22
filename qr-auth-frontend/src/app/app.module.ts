import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { VerifyComponent } from './verify/verify.component';
import {CustomerFeedbackService} from './_services/customer-feedback.service';
import {ToastrModule} from 'ngx-toastr';
import {GenerateQrComponent} from './generate-qr/generate-qr.component';
import {QrVerificationHistoryComponent} from './qr-verification-history/qr-verification-history-component';
import {MaterialModule} from './_common/module-lib/material.module';
import {NgxBootstrapModule} from './_common/module-lib/ngx-bootstrap.module';
import {ForgotPasswordDialogComponent} from './login/forgot-password-dialog/forgot-password-dialog.component';
import {ResetPasswordComponent} from './login/reset-password/reset-password.component';
import {ReportsComponent} from './reports/reports.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    VerifyComponent,
    GenerateQrComponent,
    QrVerificationHistoryComponent,
    ForgotPasswordDialogComponent,
    ResetPasswordComponent,
    ReportsComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgbModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        MaterialModule,
        NgxBootstrapModule,
        NgxChartsModule,
    ],
  providers: [
    authInterceptorProviders,
    CustomerFeedbackService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
