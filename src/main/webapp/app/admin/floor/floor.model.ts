export interface IFloor {
  id: number;
  name?: string | null;
}

export type NewFloor = Omit<IFloor, 'id'> & { id: null };
