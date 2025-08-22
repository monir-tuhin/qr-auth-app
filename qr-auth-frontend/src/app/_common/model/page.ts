import {Pageable} from './pageable';

export class Page<T> {
  content: T[] = [];
  pageable: Pageable = new Pageable();
  last!: boolean;
  totalElements!: number;
  totalPages!: number;
  size = 0;
  number!: number;
  first!: boolean;
  numberOfElements!: number;
  empty!: boolean;

  rotate = true;
  maxSize = 10;
  currentPage = 1;
  totalItems = 0;
  begSerial = 0;
  endSerial = 0;

  public getStart(size: number): number{
    return this.content.length > 0 ? (this.number * size) + 1 : 0;
  }

  public getEnd(size: number): number{
    return (this.number * size) + this.content.length;
  }
}
