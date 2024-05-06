export interface IDepot {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IDepot> = {};
