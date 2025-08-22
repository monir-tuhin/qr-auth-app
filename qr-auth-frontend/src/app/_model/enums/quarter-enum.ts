export class QuarterEnum {
  public static Q1 = new QuarterEnum('Q1');
  public static Q2 = new QuarterEnum('Q2');
  public static Q3 = new QuarterEnum('Q3');
  public static Q4 = new QuarterEnum('Q4');

  static quarterEnumList = [
    QuarterEnum.Q1,
    QuarterEnum.Q2,
    QuarterEnum.Q3,
    QuarterEnum.Q4,
  ];

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;
}
