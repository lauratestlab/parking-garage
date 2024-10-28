import { Component, OnInit, inject, signal } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFloor } from '../floor.model';
import { FloorService } from '../service/floor.service';

const floorTemplate = {} as IFloor;

const newFloor: IFloor = {
} as IFloor;

@Component({
  standalone: true,
  selector: 'app-floor-update',
  templateUrl: './floor-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export default class FloorUpdateComponent implements OnInit {
  authorities = signal<string[]>([]);
  isSaving = signal(false);

  editForm = new FormGroup({
    id: new FormControl(floorTemplate.id),
    name: new FormControl(floorTemplate.name)
  });

  private floorService = inject(FloorService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.data.subscribe(({ floor }) => {
      if (floor) {
        this.editForm.reset(floor);
      } else {
        this.editForm.reset(newFloor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const floor = this.editForm.getRawValue();
    if (floor.id !== null) {
      this.floorService.update(floor).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.floorService.create(floor).subscribe({
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
}
