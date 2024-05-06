export interface ITunnelobject {
  id?: number;
  aanleghoogte?: string | null;
  aantaltunnelbuizen?: string | null;
  breedte?: string | null;
  doorrijbreedte?: string | null;
  doorrijhoogte?: string | null;
  hoogte?: string | null;
  jaarconserveren?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  eobjectnaam?: string | null;
  eobjectnummer?: string | null;
  oppervlakte?: string | null;
  tunnelobjectmateriaal?: string | null;
}

export const defaultValue: Readonly<ITunnelobject> = {};
