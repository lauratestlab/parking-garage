import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISpot, NewSpot } from '../spot.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISpot for edit and NewSpotFormGroupInput for create.
 */
type SpotFormGroupInput = ISpot | PartialWithRequiredKeyOf<NewSpot>;

type SpotFormDefaults = Pick<NewSpot, 'id'>;

type SpotFormGroupContent = {
  id: FormControl<ISpot['id'] | NewSpot['id']>;
  name: FormControl<ISpot['name']>;
  author: FormControl<ISpot['floor']>;
};

export type SpotFormGroup = FormGroup<SpotFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SpotFormService {
  createSpotFormGroup(spot: SpotFormGroupInput = { id: null }): SpotFormGroup {
    const spotRawValue = {
      ...this.getFormDefaults(),
      ...spot,
    };
    return new FormGroup<SpotFormGroupContent>({
      id: new FormControl(
        { value: spotRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(spotRawValue.name),
      author: new FormControl(spotRawValue.floor),
    });
  }

  getSpot(form: SpotFormGroup): ISpot | NewSpot {
    return form.getRawValue() as ISpot | NewSpot;
  }

  resetForm(form: SpotFormGroup, spot: SpotFormGroupInput): void {
    const spotRawValue = { ...this.getFormDefaults(), ...spot };
    form.reset(
      {
        ...spotRawValue,
        id: { value: spotRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SpotFormDefaults {
    return {
      id: null,
    };
  }
}
