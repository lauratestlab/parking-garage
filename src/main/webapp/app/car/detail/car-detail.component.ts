import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICar } from '../car.model';

@Component({
  standalone: true,
  selector: 'app-car-detail',
  templateUrl: './car-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CarDetailComponent {
  car = input<ICar | null>(null);

  previousState(): void {
    window.history.back();
  }
}
