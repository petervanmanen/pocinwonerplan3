export interface INationaliteitingeschrevennatuurlijkpersoon {
  id?: number;
  buitenlandspersoonsnummer?: string | null;
  nationaliteit?: string | null;
  redenverkrijging?: string | null;
  redenverlies?: string | null;
}

export const defaultValue: Readonly<INationaliteitingeschrevennatuurlijkpersoon> = {};
