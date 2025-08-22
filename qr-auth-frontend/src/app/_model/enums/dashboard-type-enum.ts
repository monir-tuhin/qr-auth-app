export class DashboardTypeEnum {
  public static STATUS = new DashboardTypeEnum('STATUS');
  public static METHOD = new DashboardTypeEnum('METHOD');

  constructor(enumKey: string) {
    this.enumKey = enumKey;
  }

  readonly enumKey: string;
}
