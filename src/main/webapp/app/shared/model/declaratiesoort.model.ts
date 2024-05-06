export interface IDeclaratiesoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IDeclaratiesoort> = {};
