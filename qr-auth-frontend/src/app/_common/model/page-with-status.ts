export class PageWithStatus {
  loadingMode: boolean = false;
  page: number = 0;
  size: number = 10;

  public constructor(o?: Partial<PageWithStatus>) {
    Object.assign(this, o);
  }
}
