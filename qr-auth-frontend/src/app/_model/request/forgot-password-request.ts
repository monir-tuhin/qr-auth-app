export class ForgotPasswordRequest {
  email!: string;
  mobile!: string;

  constructor(obj?: Partial<ForgotPasswordRequest>) {
    Object.assign(this, obj);
  }
}
