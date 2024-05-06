export interface ILeidingelement {
  id?: number;
  afwijkendedieptelegging?: string | null;
  diepte?: string | null;
  geonauwkeurigheidxy?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  leverancier?: string | null;
  themaimkl?: string | null;
}

export const defaultValue: Readonly<ILeidingelement> = {};
