export interface IGebouwdobject {
  id?: number;
  bouwkundigebestemmingactueel?: string | null;
  brutoinhoud?: string | null;
  identificatie?: string | null;
  inwinningoppervlakte?: string | null;
  oppervlakteobject?: string | null;
  statusvoortgangbouw?: string | null;
}

export const defaultValue: Readonly<IGebouwdobject> = {};
