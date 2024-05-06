export interface IScheiding {
  id?: number;
  aanleghoogte?: string | null;
  breedte?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  eobjectnaam?: string | null;
  eobjectnummer?: string | null;
  oppervlakte?: string | null;
  scheidingmateriaal?: string | null;
  verplaatsbaar?: boolean | null;
}

export const defaultValue: Readonly<IScheiding> = {
  verplaatsbaar: false,
};
