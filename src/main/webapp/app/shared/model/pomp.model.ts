export interface IPomp {
  id?: number;
  aanslagniveau?: string | null;
  beginstanddraaiurenteller?: string | null;
  besturingskast?: string | null;
  laatstestanddraaiurenteller?: string | null;
  laatstestandkwhteller?: string | null;
  levensduur?: string | null;
  model?: string | null;
  motorvermogen?: string | null;
  onderdeelmetpomp?: string | null;
  ontwerpcapaciteit?: string | null;
  pompcapaciteit?: string | null;
  serienummer?: string | null;
  type?: string | null;
  typeonderdeelmetpomp?: string | null;
  typeplus?: string | null;
  typewaaier?: string | null;
  uitslagpeil?: string | null;
}

export const defaultValue: Readonly<IPomp> = {};
