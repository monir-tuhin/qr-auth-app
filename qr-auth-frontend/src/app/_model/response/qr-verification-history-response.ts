import {VerificationStatusEnum} from '../enums/verification-status-enum';

export class QrVerificationHistoryResponse {
  id!: number;
  uid!: string;
  sl!: number;
  verificationScanStatusEnum!: VerificationStatusEnum;
  qrVerificationCountScan!: number;
  verificationSmsStatusEnum!: VerificationStatusEnum;
  qrVerificationCountSms!: number;
  updatedAt!: string;
  lastQrVerificationScanAt!: string;
  lastQrVerificationScanAtFormatted!: string;
  lastQrVerificationSmsAt!: string;
  lastQrVerificationSmsAtFormatted!: string;
  brandKey!: string;
  isLoading!: boolean;

  public constructor(o?: Partial<QrVerificationHistoryResponse>) {
    Object.assign(this, o);
  }
}
