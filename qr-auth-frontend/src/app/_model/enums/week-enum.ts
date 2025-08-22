export class WeekEnum {
  public static WEEK_1 = new WeekEnum('WEEK_1');
  public static WEEK_2 = new WeekEnum('WEEK_2');
  public static WEEK_3 = new WeekEnum('WEEK_3');
  public static WEEK_4 = new WeekEnum('WEEK_4');

  static weekEnumList = [
    WeekEnum.WEEK_1,
    WeekEnum.WEEK_2,
    WeekEnum.WEEK_3,
    WeekEnum.WEEK_4,
  ];

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;
}
