export interface IOveriggebouwdobject {
  id?: number;
  bouwjaar?: string | null;
  indicatieplanobject?: string | null;
  overiggebouwdobjectidentificatie?: string | null;
}

export const defaultValue: Readonly<IOveriggebouwdobject> = {};
