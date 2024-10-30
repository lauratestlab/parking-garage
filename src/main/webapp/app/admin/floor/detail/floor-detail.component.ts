import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IFloor } from '../floor.model';

@Component({
  standalone: true,
  selector: 'app-floor-detail',
  templateUrl: './floor-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FloorDetailComponent {
  floor = input<IFloor | null>(null);

  previousState(): void {
    window.history.back();
  }
}
