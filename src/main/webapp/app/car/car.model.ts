export interface ICar {
  id: number | null;
  name: string | null;
}

export class Car implements ICar {
  constructor(
    public id: number | null,
    public name: string | null,
  ) {}
}