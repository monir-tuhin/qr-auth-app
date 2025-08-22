export class ValidationStatusEnum {
  public static VALID = new ValidationStatusEnum('VALID');
  public static INVALID = new ValidationStatusEnum('INVALID');
  public static USED = new ValidationStatusEnum('USED');
  public static MULTI_ATTEMPT = new ValidationStatusEnum('MULTI_ATTEMPT');

  static validationStatusEnumList = [
    ValidationStatusEnum.VALID,
    ValidationStatusEnum.INVALID,
    ValidationStatusEnum.USED,
    ValidationStatusEnum.MULTI_ATTEMPT,
  ];

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;
}
