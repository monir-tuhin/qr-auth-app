export class SignupRequest {
  id!: number;
  fullName!: string;
  username!: string;
  email!: string;
  mobileNumber!: string;
  password!: string;
  roles!: Array<string>;

  constructor(obj?: Partial<SignupRequest>) {
    Object.assign(this, obj);
  }
}
