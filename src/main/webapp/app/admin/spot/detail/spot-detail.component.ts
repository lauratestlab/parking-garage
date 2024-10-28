import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';

import { Spot } from '../spot.model';

@Component({
  standalone: true,
  selector: 'app-spot-detail',
  templateUrl: './spot-detail.component.html',
  imports: [RouterModule, SharedModule],
})
export default class SpotDetailComponent {
  spot = input<Spot | null>(null);
}
