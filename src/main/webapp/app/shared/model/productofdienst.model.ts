export interface IProductofdienst {
  id?: number;
  afhandeltijd?: string | null;
  ingebruik?: string | null;
  naam?: string | null;
}

export const defaultValue: Readonly<IProductofdienst> = {};
