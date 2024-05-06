export interface ILeiding {
  id?: number;
  afwijkendedieptelegging?: string | null;
  breedte?: string | null;
  diameter?: string | null;
  diepte?: string | null;
  eisvoorzorgsmaatregel?: string | null;
  geonauwkeurigheidxy?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  materiaal?: string | null;
  themaimkl?: string | null;
  verhoogdrisico?: string | null;
}

export const defaultValue: Readonly<ILeiding> = {};
