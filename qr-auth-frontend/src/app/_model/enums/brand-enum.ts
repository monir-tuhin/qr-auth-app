export class BrandEnum {
  public static WHITE_TONE = new BrandEnum('WT', 'White Tone');
  public static OSSUM = new BrandEnum('OM', 'Ossum');
  public static REALMAN = new BrandEnum('RM', 'Realman');
  public static FOGG = new BrandEnum('FG', 'Fogg');

  static brandEnumList = [
    BrandEnum.WHITE_TONE,
    BrandEnum.OSSUM,
    BrandEnum.REALMAN,
    BrandEnum.FOGG,
  ];

  constructor(enumKey: string, enumValue: string) {
    this.enumKey = enumKey;
    this.enumValue = enumValue;
  }
  readonly enumKey: string;
  readonly enumValue: string;

  public static getValueByEnumKey(itemEnum: BrandEnum): string {
    return this.brandEnumList.filter(itm => itm.enumKey === itemEnum.enumKey)[0].enumValue;
  }
  public static getKeyByEnumValue(itemEnum: string): string {
    return this.brandEnumList.filter(itm => itm.enumValue === itemEnum)[0].enumKey;
  }

}
