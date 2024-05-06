export interface IOorspronkelijkefunctie {
  id?: number;
  functie?: string | null;
  functiesoort?: string | null;
  hoofdcategorie?: string | null;
  hoofdfunctie?: string | null;
  subcategorie?: string | null;
  toelichting?: string | null;
  verbijzondering?: string | null;
}

export const defaultValue: Readonly<IOorspronkelijkefunctie> = {};
