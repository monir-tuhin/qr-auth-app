import {VerificationStatusEnum} from '../enums/verification-status-enum';

export class QrCodeVerificationResponse {
  id!: number;
  uid!: string;
  sl!: number;
  verificationScanStatusEnum!: VerificationStatusEnum;
  qrVerificationCountScan!: number;
  verificationSmsStatusEnum!: VerificationStatusEnum;
  qrVerificationCountSms!: number;
  customerFeedbackSubmitted!: boolean;
}
