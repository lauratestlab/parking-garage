import { IFloor } from 'app/admin/floor/floor.model';

export interface ISpot {
  id: number;
  name?: string | null;
  floor?: Pick<IFloor, 'id' | 'name'> | null;
}

export type NewSpot = Omit<ISpot, 'id'> & { id: null };
