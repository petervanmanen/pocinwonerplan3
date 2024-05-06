export interface IVoorzieningsoort {
  id?: number;
  code?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  productcategorie?: string | null;
  productcategoriecode?: string | null;
  productcode?: string | null;
  wet?: string | null;
}

export const defaultValue: Readonly<IVoorzieningsoort> = {};
