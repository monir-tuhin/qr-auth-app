import { ValidationMethodsEnum } from '../../enums/validation-methods-enum';
import {ValidationStatusEnum} from '../../enums/validation-status-enum';
import {WeekEnum} from '../../enums/week-enum';
import {QuarterEnum} from '../../enums/quarter-enum';

export class QrValidationSearchDto {
  uid!: string;
  validationMethodsEnum!: string;
  validationStatusEnum!: string;
  mobileNumber!: string;
  validationDate!: string;
  weekEnum!: string;
  month!: string;
  quarterEnum!: string;
  year!: number;
  searchTerm!: string;
  chartTypeEnum!: string;
  dashboardTypeEnum!: string;

  public constructor(o?: Partial<QrValidationSearchDto>) {
    Object.assign(this, o);
  }
}
