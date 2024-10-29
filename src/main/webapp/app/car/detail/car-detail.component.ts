import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';

import { Car } from '../car.model';

@Component({
  standalone: true,
  selector: 'app-user-mgmt-detail',
  templateUrl: './car-detail.component.html',
  imports: [RouterModule, SharedModule],
})
export default class UserManagementDetailComponent {
  user = input<Car | null>(null);
}
