import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import {VerifyComponent} from './verify/verify.component';
import {GenerateQrComponent} from './generate-qr/generate-qr.component';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {QrVerificationHistoryComponent} from './qr-verification-history/qr-verification-history-component';
import {ResetPasswordComponent} from './login/reset-password/reset-password.component';
import {ReportsComponent} from './reports/reports.component';
import {AuthGuard} from './auth.guard';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  { path: 'user', component: BoardUserComponent, canActivate: [AuthGuard] },
  { path: 'mod', component: BoardModeratorComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: BoardAdminComponent, canActivate: [AuthGuard] },
  { path: 'generate-qr', component: GenerateQrComponent, canActivate: [AuthGuard] },
  { path: 'qr-verification-history', component: QrVerificationHistoryComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] },
  { path: 'verify', component: VerifyComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}]
})
export class AppRoutingModule { }
