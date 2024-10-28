import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { Spot } from '../spot.model';
import { SpotService } from '../service/spot.service';

@Component({
  standalone: true,
  selector: 'app-spot-delete-dialog',
  templateUrl: './spot-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export default class SpotManagementDeleteDialogComponent {
  spot?: Spot;

  private spotService = inject(SpotService);
  private activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(login: number): void {
    this.spotService.delete(login).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
