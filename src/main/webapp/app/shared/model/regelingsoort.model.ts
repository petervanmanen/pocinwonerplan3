export interface IRegelingsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IRegelingsoort> = {};
