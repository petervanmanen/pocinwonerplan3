export interface IPaal {
  id?: number;
  breedte?: string | null;
  diameter?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  materiaal?: string | null;
  vorm?: string | null;
}

export const defaultValue: Readonly<IPaal> = {};
