export interface IVaartuig {
  id?: number;
  breedte?: string | null;
  hoogte?: string | null;
  kleur?: string | null;
  lengte?: string | null;
  naamvaartuig?: string | null;
  registratienummer?: string | null;
}

export const defaultValue: Readonly<IVaartuig> = {};
