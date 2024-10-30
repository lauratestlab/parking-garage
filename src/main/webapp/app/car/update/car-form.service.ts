import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICar, NewCar } from '../car.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICar for edit and NewCarFormGroupInput for create.
 */
type CarFormGroupInput = ICar | PartialWithRequiredKeyOf<NewCar>;

type CarFormDefaults = Pick<NewCar, 'id'>;

type CarFormGroupContent = {
  id: FormControl<ICar['id'] | NewCar['id']>;
  model: FormControl<ICar['model']>;
  make: FormControl<ICar['make']>;
  color: FormControl<ICar['color']>;
  registration: FormControl<ICar['registration']>;
  user: FormControl<ICar['user']>;
};

export type CarFormGroup = FormGroup<CarFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarFormService {
  createCarFormGroup(car: CarFormGroupInput = { id: null }): CarFormGroup {
    const carRawValue = {
      ...this.getFormDefaults(),
      ...car,
    };
    return new FormGroup<CarFormGroupContent>({
      id: new FormControl(
        { value: carRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      model: new FormControl(carRawValue.model, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(25)],
      }),
      make: new FormControl(carRawValue.make, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(25)],
      }),
      color: new FormControl(carRawValue.color, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(25)],
      }),
      registration: new FormControl(carRawValue.registration, {
        validators: [Validators.required, Validators.minLength(1)],
      }),
      user: new FormControl(carRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getCar(form: CarFormGroup): ICar | NewCar {
    return form.getRawValue() as ICar | NewCar;
  }

  resetForm(form: CarFormGroup, car: CarFormGroupInput): void {
    const carRawValue = { ...this.getFormDefaults(), ...car };
    form.reset(
      {
        ...carRawValue,
        id: { value: carRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarFormDefaults {
    return {
      id: null,
    };
  }
}
