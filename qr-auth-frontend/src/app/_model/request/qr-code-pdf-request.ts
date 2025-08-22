export class QrCodePdfRequest {
  countFrom!: number;
  countTo!: number;

  constructor(obj?: Partial<QrCodePdfRequest>) {
    Object.assign(this, obj);
  }
}
