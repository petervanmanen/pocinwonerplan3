export interface IBak {
  id?: number;
  breedte?: string | null;
  diameter?: string | null;
  gewichtleeg?: string | null;
  gewichtvol?: string | null;
  hoogte?: string | null;
  inhoud?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  materiaal?: string | null;
  verplaatsbaar?: boolean | null;
  vorm?: string | null;
}

export const defaultValue: Readonly<IBak> = {
  verplaatsbaar: false,
};
