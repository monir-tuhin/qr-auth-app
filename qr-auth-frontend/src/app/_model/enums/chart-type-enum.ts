export class ChartTypeEnum {
  public static YEAR = new ChartTypeEnum('YEAR');
  public static QUARTER = new ChartTypeEnum('QUARTER');
  public static MONTH = new ChartTypeEnum('MONTH');
  public static WEEK = new ChartTypeEnum('WEEK');

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;
}
