export interface IBord {
  id?: number;
  breedte?: string | null;
  diameter?: string | null;
  drager?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  materiaal?: string | null;
  vorm?: string | null;
}

export const defaultValue: Readonly<IBord> = {};
