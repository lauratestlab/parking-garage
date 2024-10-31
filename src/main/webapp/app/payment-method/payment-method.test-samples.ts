import { IPaymentMethod, NewPaymentMethod } from './payment-method.model';

export const sampleWithRequiredData: IPaymentMethod = {
  id: 6903,
  expirationDate: '07/67',
  ccv: 6754,
  cardNumber: 'holster whoa',
  fullName: 'institute even',
  street: 'Billy Knoll',
  city: 'New Art',
  state: 'refine opposite',
  zip: 'hydrocarbon',
};

export const sampleWithPartialData: IPaymentMethod = {
  id: 12904,
  expirationDate: '09/45',
  ccv: 22871,
  cardNumber: 'gazebo worth',
  fullName: 'animated well spotless',
  street: 'Bettye Crescent',
  city: 'Sheridanborough',
  state: 'whale neatly',
  zip: 'oblong',
};

export const sampleWithFullData: IPaymentMethod = {
  id: 19697,
  expirationDate: '03/67',
  ccv: 6878,
  cardNumber: 'grave',
  fullName: 'tough uh-huh',
  street: 'Cordia Crossing',
  city: 'Highland',
  state: 'incidentally fooey',
  zip: 'potentially bravely',
};

export const sampleWithNewData: NewPaymentMethod = {
  expirationDate: '05/95',
  ccv: 20796,
  cardNumber: 'yet beyond recklessly',
  fullName: 'which because ashamed',
  street: 'Cliff Road',
  city: 'Palmdale',
  state: 'since',
  zip: 'approximate uneven secrecy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
