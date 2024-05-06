export interface IZelfredzaamheidmatrix {
  id?: number;
  datumeindegeldigheid?: string | null;
  datumstartgeldigheid?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IZelfredzaamheidmatrix> = {};
