import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 29981,
  login: 'oZ',
};

export const sampleWithPartialData: IUser = {
  id: 5862,
  login: '8a',
};

export const sampleWithFullData: IUser = {
  id: 8015,
  login: 'RJZ@a',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
