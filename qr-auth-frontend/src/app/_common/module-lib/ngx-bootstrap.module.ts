import {NgModule} from '@angular/core';
import {PaginationModule} from 'ngx-bootstrap';

@NgModule({
  imports: [
    PaginationModule.forRoot(),
  ],
  exports: [
    PaginationModule,
  ]
})
export class NgxBootstrapModule { }
