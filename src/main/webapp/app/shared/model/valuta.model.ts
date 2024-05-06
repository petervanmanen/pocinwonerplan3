export interface IValuta {
  id?: number;
  datumbegingeldigheid?: string | null;
  datumeindegeldigheid?: string | null;
  naam?: string | null;
  valutacode?: string | null;
}

export const defaultValue: Readonly<IValuta> = {};
