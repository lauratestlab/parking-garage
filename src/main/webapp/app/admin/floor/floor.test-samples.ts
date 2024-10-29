import { IFloor, NewFloor } from './floor.model';

export const sampleWithRequiredData: IFloor = {
  id: 20225,
  name: 'very usually',
};

export const sampleWithPartialData: IFloor = {
  id: 218,
  name: 'lightly',
};

export const sampleWithFullData: IFloor = {
  id: 22601,
  name: 'wombat nor',
};

export const sampleWithNewData: NewFloor = {
  name: 'frightfully solidly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
