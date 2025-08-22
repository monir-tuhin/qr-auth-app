import {VerificationStatusEnum} from '../../enums/verification-status-enum';
import {PageWithStatus} from '../../../_common/model/page-with-status';

export class QrVerificationHistorySearchDto extends PageWithStatus{
  uid!: string;
  sl!: number;
  verificationStatusEnum!: VerificationStatusEnum;
  lastQrVerification!: string;
  searchTerm!: string;

  public constructor(o?: Partial<QrVerificationHistorySearchDto>) {
    super();
    Object.assign(this, o);
  }
}
