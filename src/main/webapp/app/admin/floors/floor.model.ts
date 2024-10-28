export interface IFloor {
  id: number | null;
  name: string | null;
}

export class Floor implements IFloor {
  constructor(
    public id: number | null,
    public name: string | null,
  ) {}
}
