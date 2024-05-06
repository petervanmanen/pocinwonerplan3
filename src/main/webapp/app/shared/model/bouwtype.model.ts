export interface IBouwtype {
  id?: number;
  hoofdcategorie?: string | null;
  subcategorie?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IBouwtype> = {};
