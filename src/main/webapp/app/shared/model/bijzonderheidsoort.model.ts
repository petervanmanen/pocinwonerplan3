export interface IBijzonderheidsoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IBijzonderheidsoort> = {};
