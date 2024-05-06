export interface IResultaatsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IResultaatsoort> = {};
