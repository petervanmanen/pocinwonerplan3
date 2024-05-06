export interface IFunctie {
  id?: number;
  groep?: string | null;
  naam?: string | null;
}

export const defaultValue: Readonly<IFunctie> = {};
