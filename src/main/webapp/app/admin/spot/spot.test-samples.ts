import { ISpot, NewSpot } from './spot.model';

export const sampleWithRequiredData: ISpot = {
  id: 23961,
  name: 'reopen nor',
};

export const sampleWithPartialData: ISpot = {
  id: 24593,
  name: 'valiantly',
};

export const sampleWithFullData: ISpot = {
  id: 30208,
  name: 'pace',
};

export const sampleWithNewData: NewSpot = {
  name: 'detain atomize insec',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
