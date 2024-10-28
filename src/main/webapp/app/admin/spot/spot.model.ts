import {IFloor} from "../floors/floor.model";

export interface ISpot {
  id: number;
  name?: string | null;
  floor?: Pick<IFloor, 'id'> | null;
}

export type NewSpot = Omit<ISpot, 'id'> & { id: null };