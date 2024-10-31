import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentMethod for edit and NewPaymentMethodFormGroupInput for create.
 */
type PaymentMethodFormGroupInput = IPaymentMethod | PartialWithRequiredKeyOf<NewPaymentMethod>;

type PaymentMethodFormDefaults = Pick<NewPaymentMethod, 'id'>;

type PaymentMethodFormGroupContent = {
  id: FormControl<IPaymentMethod['id'] | NewPaymentMethod['id']>;
  expirationDate: FormControl<IPaymentMethod['expirationDate']>;
  ccv: FormControl<IPaymentMethod['ccv']>;
  cardNumber: FormControl<IPaymentMethod['cardNumber']>;
  fullName: FormControl<IPaymentMethod['fullName']>;
  street: FormControl<IPaymentMethod['street']>;
  city: FormControl<IPaymentMethod['city']>;
  state: FormControl<IPaymentMethod['state']>;
  zip: FormControl<IPaymentMethod['zip']>;
};

export type PaymentMethodFormGroup = FormGroup<PaymentMethodFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodFormService {
  createPaymentMethodFormGroup(paymentMethod: PaymentMethodFormGroupInput = { id: null }): PaymentMethodFormGroup {
    const paymentMethodRawValue = {
      ...this.getFormDefaults(),
      ...paymentMethod,
    };
    return new FormGroup<PaymentMethodFormGroupContent>({
      id: new FormControl(
        { value: paymentMethodRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      expirationDate: new FormControl(paymentMethodRawValue.expirationDate, {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(5),
          Validators.pattern('^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$'),
        ],
      }),
      ccv: new FormControl(paymentMethodRawValue.ccv, {
        validators: [Validators.required],
      }),
      cardNumber: new FormControl(paymentMethodRawValue.cardNumber, {
        validators: [Validators.required],
      }),
      fullName: new FormControl(paymentMethodRawValue.fullName, {
        validators: [Validators.required],
      }),
      street: new FormControl(paymentMethodRawValue.street, {
        validators: [Validators.required],
      }),
      city: new FormControl(paymentMethodRawValue.city, {
        validators: [Validators.required],
      }),
      state: new FormControl(paymentMethodRawValue.state, {
        validators: [Validators.required],
      }),
      zip: new FormControl(paymentMethodRawValue.zip, {
        validators: [Validators.required],
      }),
    });
  }

  getPaymentMethod(form: PaymentMethodFormGroup): IPaymentMethod | NewPaymentMethod {
    return form.getRawValue() as IPaymentMethod | NewPaymentMethod;
  }

  resetForm(form: PaymentMethodFormGroup, paymentMethod: PaymentMethodFormGroupInput): void {
    const paymentMethodRawValue = { ...this.getFormDefaults(), ...paymentMethod };
    form.reset(
      {
        ...paymentMethodRawValue,
        id: { value: paymentMethodRawValue.id, disabled: true },
      } as any
    );
  }

  private getFormDefaults(): PaymentMethodFormDefaults {
    return {
      id: null,
    };
  }
}
