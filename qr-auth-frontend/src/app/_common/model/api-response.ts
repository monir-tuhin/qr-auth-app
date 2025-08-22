
export class ApiResponse<T> {
  data!: T;
  httpStatusCode!: number;
  status!: boolean;
  message!: string;

  public constructor(o?: Partial<ApiResponse<T>>) {
    Object.assign(this, o);
  }
}
