export interface INaamaanschrijvingnatuurlijkpersoon {
  id?: number;
  aanhefaanschrijving?: string | null;
  geslachtsnaamaanschrijving?: string | null;
  voorlettersaanschrijving?: string | null;
  voornamenaanschrijving?: string | null;
}

export const defaultValue: Readonly<INaamaanschrijvingnatuurlijkpersoon> = {};
