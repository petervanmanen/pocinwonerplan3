export interface INaamgebruiknatuurlijkpersoon {
  id?: number;
  aanhefaanschrijving?: string | null;
  adellijketitelnaamgebruik?: string | null;
  geslachtsnaamstamnaamgebruik?: string | null;
}

export const defaultValue: Readonly<INaamgebruiknatuurlijkpersoon> = {};
