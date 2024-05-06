export interface IEobject {
  id?: number;
  adresbinnenland?: string | null;
  adresbuitenland?: string | null;
  domein?: string | null;
  geometrie?: string | null;
  identificatie?: string | null;
  indicatierisico?: string | null;
  kadastraleaanduiding?: string | null;
  naam?: string | null;
  eobjecttype?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IEobject> = {};
