import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFloor } from '../floor.model';
import { FloorService } from '../service/floor.service';
import { FloorFormGroup, FloorFormService } from './floor-form.service';

@Component({
  standalone: true,
  selector: 'app-floor-update',
  templateUrl: './floor-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FloorUpdateComponent implements OnInit {
  isSaving = false;
  floor: IFloor | null = null;

  protected floorService = inject(FloorService);
  protected floorFormService = inject(FloorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FloorFormGroup = this.floorFormService.createFloorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ floor }) => {
      this.floor = floor;
      if (floor) {
        this.updateForm(floor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const floor = this.floorFormService.getFloor(this.editForm);
    if (floor.id !== null) {
      this.subscribeToSaveResponse(this.floorService.update(floor));
    } else {
      this.subscribeToSaveResponse(this.floorService.create(floor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFloor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(floor: IFloor): void {
    this.floor = floor;
    this.floorFormService.resetForm(this.editForm, floor);
  }
}
