export interface IBouwactiviteit {
  id?: number;
  bouwjaarklasse?: string | null;
  bouwjaartot?: string | null;
  bouwjaarvan?: string | null;
  indicatie?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IBouwactiviteit> = {};
