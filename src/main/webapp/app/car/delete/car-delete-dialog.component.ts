import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { Car } from '../car.model';
import { CarService } from '../service/car.service';

@Component({
  standalone: true,
  selector: 'app-user-mgmt-delete-dialog',
  templateUrl: './car-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export default class CarManagementDeleteDialogComponent {
  car?: Car;

  private userService = inject(CarService);
  private activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(login: number): void {
    this.userService.delete(login).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
