export interface INaamnatuurlijkpersoon {
  id?: number;
  adellijketitelofpredikaat?: string | null;
  geslachtsnaam?: string | null;
  voornamen?: string | null;
  voorvoegselgeslachtsnaam?: string | null;
}

export const defaultValue: Readonly<INaamnatuurlijkpersoon> = {};
