import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';

import { Floor } from '../floor.model';

@Component({
  standalone: true,
  selector: 'app-user-mgmt-detail',
  templateUrl: './floor-detail.component.html',
  imports: [RouterModule, SharedModule],
})
export default class UserManagementDetailComponent {
  user = input<Floor | null>(null);
}
