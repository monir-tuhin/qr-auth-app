import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import {NgModule} from '@angular/core';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  imports: [
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatProgressBarModule,
    MatDialogModule,
  ],
  exports: [
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatProgressBarModule,
    MatDialogModule,
  ]
})
export class MaterialModule { }
