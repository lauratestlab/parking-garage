import { Component, OnInit, inject, signal } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ISpot } from '../spot.model';
import { SpotService } from '../service/spot.service';
import {IFloor} from "../../floors/floor.model";
import {FloorService} from "../../floors/service/floor.service";
import {map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";

const spotTemplate = {} as ISpot;

const newSpot: ISpot = {
} as ISpot;

@Component({
  standalone: true,
  selector: 'app-spot-update',
  templateUrl: './spot-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SpotUpdateComponent implements OnInit {
  isSaving = signal(false);
  spot: ISpot | null = null;

  floorsSharedCollection: IFloor[] = [];

  private spotService = inject(SpotService);
  protected bookFormService = inject(SpotFormService);
  private floorService = inject(FloorService);
  private route = inject(ActivatedRoute);

  editForm = new FormGroup({
    id: new FormControl(spotTemplate.id),
    name: new FormControl(spotTemplate.name),
    floor: new FormControl(spotTemplate.floor)
  });


  compareFloor = (o1: IFloor | null, o2: IFloor | null): boolean => this.floorService.compareFloor(o1, o2);




  ngOnInit(): void {
    this.route.data.subscribe(({ spot }) => {
      if (spot) {
        this.editForm.reset(spot);
      } else {
        this.editForm.reset(newSpot);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const spot = this.editForm.getRawValue();
    if (spot.id !== null) {
      this.spotService.update(spot).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.spotService.create(spot).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.isSaving.set(false);
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving.set(false);
  }

  protected updateForm(spot: ISpot): void {
    this.spot = spot;
    this.spotFormService.resetForm(this.editForm, spot);

    this.floorsSharedCollection = this.floorService.addFloorToCollectionIfMissing<IFloor>(this.floorsSharedCollection, spot.floor);
  }

  protected loadRelationshipsOptions(): void {
    this.floorService
        .query()
        .pipe(map((res: HttpResponse<IFloor[]>) => res.body ?? []))
        .pipe(map((floors: IFloor[]) => this.floorService.addFloorToCollectionIfMissing<IFloor>(floors, this.spot?.floor)))
        .subscribe((floors: IFloor[]) => (this.floorsSharedCollection = floors));
  }
}
