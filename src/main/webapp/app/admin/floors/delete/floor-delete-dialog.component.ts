import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { Floor } from '../floor.model';
import { FloorService } from '../service/floor.service';

@Component({
  standalone: true,
  selector: 'app-user-mgmt-delete-dialog',
  templateUrl: './floor-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export default class FloorManagementDeleteDialogComponent {
  floor?: Floor;

  private userService = inject(FloorService);
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
