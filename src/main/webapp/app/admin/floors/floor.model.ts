export interface IFloor {
  floorId: number | null;
  name?: string;
}

export class Floor implements IFloor {
  constructor(
    public floorId: number | null,
    public name?: string,
  ) {}
}
