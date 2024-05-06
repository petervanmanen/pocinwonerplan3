export interface IVerzuimsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IVerzuimsoort> = {};
