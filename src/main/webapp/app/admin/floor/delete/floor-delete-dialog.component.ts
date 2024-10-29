import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFloor } from '../floor.model';
import { FloorService } from '../service/floor.service';

@Component({
  standalone: true,
  templateUrl: './floor-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FloorDeleteDialogComponent {
  floor?: IFloor;

  protected floorService = inject(FloorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.floorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
