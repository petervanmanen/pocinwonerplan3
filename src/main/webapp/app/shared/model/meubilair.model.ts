import dayjs from 'dayjs';

export interface IMeubilair {
  id?: number;
  aanleghoogte?: string | null;
  bouwjaar?: string | null;
  breedte?: string | null;
  datumaanschaf?: dayjs.Dayjs | null;
  diameter?: string | null;
  fabrikant?: string | null;
  gewicht?: string | null;
  hoogte?: string | null;
  installateur?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  jaarpraktischeinde?: string | null;
  kleur?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  meubilairmateriaal?: string | null;
  model?: string | null;
  ondergrond?: string | null;
  oppervlakte?: string | null;
  prijsaanschaf?: string | null;
  serienummer?: string | null;
  transponder?: string | null;
  transponderlocatie?: string | null;
  typefundering?: string | null;
  typeplaat?: boolean | null;
}

export const defaultValue: Readonly<IMeubilair> = {
  typeplaat: false,
};
