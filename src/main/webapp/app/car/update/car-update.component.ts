import { Component, OnInit, inject, signal } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICar } from '../car.model';
import { CarService } from '../service/car.service';

const carTemplate = {} as ICar;

const newCar: ICar = {
} as ICar;

@Component({
  standalone: true,
  selector: 'app-user-mgmt-update',
  templateUrl: './car-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export default class UserManagementUpdateComponent implements OnInit {
  authorities = signal<string[]>([]);
  isSaving = signal(false);

  editForm = new FormGroup({
    id: new FormControl(carTemplate.id),
    name: new FormControl(carTemplate.name)
  });

  private carService = inject(CarService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.data.subscribe(({ car }) => {
      if (car) {
        this.editForm.reset(car);
      } else {
        this.editForm.reset(newCar);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const car = this.editForm.getRawValue();
    if (car.id !== null) {
      this.carService.update(car).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.carService.create(car).subscribe({
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
