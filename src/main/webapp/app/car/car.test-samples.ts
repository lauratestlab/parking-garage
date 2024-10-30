import { ICar, NewCar } from './car.model';

export const sampleWithRequiredData: ICar = {
  id: 28243,
  model: 'viciously besides down',
  make: 'toothpick',
  color: 'tan',
  registration: 'unless similar happy-go-lucky',
};

export const sampleWithPartialData: ICar = {
  id: 17685,
  model: 'rightfully comestible giv',
  make: 'oh bicycle near',
  color: 'white',
  registration: 'garage however huzzah',
};

export const sampleWithFullData: ICar = {
  id: 18258,
  model: 'ack phooey',
  make: 'inasmuch',
  color: 'indigo',
  registration: 'after between',
};

export const sampleWithNewData: NewCar = {
  model: 'shadowbox',
  make: 'management contractor',
  color: 'orange',
  registration: 'twist soon aha',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
