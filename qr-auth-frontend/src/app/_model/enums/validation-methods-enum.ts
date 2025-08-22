export class ValidationMethodsEnum {
  public static SCAN = new ValidationMethodsEnum('SCAN');
  public static SMS = new ValidationMethodsEnum('SMS');

  static validationMethodsEnumList = [
    ValidationMethodsEnum.SCAN,
    ValidationMethodsEnum.SMS,
  ];

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;

}
