import { IUser } from 'app/user/user.model';

export interface IPaymentMethod {
  id: number;
  expirationDate?: string | null;
  ccv?: number | null;
  cardNumber?: string | null;
  fullName?: string | null;
  street?: string | null;
  city?: string | null;
  state?: string | null;
  zip?: string | null;
}

export type NewPaymentMethod = Omit<IPaymentMethod, 'id'> & { id: null };
