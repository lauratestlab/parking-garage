import { IUser } from 'app/user/user.model';

export interface ICar {
  id: number;
  model?: string | null;
  make?: string | null;
  color?: string | null;
  registration?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewCar = Omit<ICar, 'id'> & { id: null };
