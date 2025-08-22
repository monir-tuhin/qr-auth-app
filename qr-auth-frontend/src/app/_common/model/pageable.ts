export class Pageable {
  offset!: number;
  pageNumber = 0;
  pageSize!: number;
  unpaged!: boolean;
  paged!: boolean;

  public constructor(init?: Partial<Pageable>) {
    Object.assign(this, init);
  }
}
