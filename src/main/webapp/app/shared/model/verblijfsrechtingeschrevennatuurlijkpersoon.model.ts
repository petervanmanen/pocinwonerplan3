export interface IVerblijfsrechtingeschrevennatuurlijkpersoon {
  id?: number;
  aanduidingverblijfsrecht?: string | null;
  datumaanvangverblijfsrecht?: string | null;
  datummededelingverblijfsrecht?: string | null;
  datumvoorzieneindeverblijfsrecht?: string | null;
}

export const defaultValue: Readonly<IVerblijfsrechtingeschrevennatuurlijkpersoon> = {};
