export interface IDoelstellingsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IDoelstellingsoort> = {};
