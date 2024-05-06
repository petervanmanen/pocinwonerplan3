export interface ISamengesteldenaamnatuurlijkpersoon {
  id?: number;
  adellijketitel?: string | null;
  geslachtsnaamstam?: string | null;
  namenreeks?: string | null;
  predicaat?: string | null;
  scheidingsteken?: string | null;
  voornamen?: string | null;
  voorvoegsel?: string | null;
}

export const defaultValue: Readonly<ISamengesteldenaamnatuurlijkpersoon> = {};
