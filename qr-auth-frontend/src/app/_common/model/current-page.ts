export class CurrentPage {
  page!: number;
  size: number = 10;

  public constructor(o?: Partial<CurrentPage>) {
    Object.assign(this, o);
  }
}
