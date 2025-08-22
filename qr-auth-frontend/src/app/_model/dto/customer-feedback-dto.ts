export class CustomerFeedbackDto {
  id!: number;
  name!: string;
  mobileNumber!: string;
  purchaseLocation!: string;
  opinion!: string;
  productUid!: string;

  constructor(obj?: Partial<CustomerFeedbackDto>) {
      Object.assign(this, obj);
  }

}
