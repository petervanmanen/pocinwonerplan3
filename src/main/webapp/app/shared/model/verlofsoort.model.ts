export interface IVerlofsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IVerlofsoort> = {};
