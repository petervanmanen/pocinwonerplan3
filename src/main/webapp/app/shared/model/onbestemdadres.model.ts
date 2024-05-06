export interface IOnbestemdadres {
  id?: number;
  huisletter?: string | null;
  huisnummer?: string | null;
  huisnummertoevoeging?: string | null;
  postcode?: string | null;
  straatnaam?: string | null;
}

export const defaultValue: Readonly<IOnbestemdadres> = {};
