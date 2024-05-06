export interface IBalieverkoopentreekaart {
  id?: number;
  datumeindegeldigheid?: string | null;
  datumstart?: string | null;
  gebruiktop?: string | null;
  rondleiding?: string | null;
}

export const defaultValue: Readonly<IBalieverkoopentreekaart> = {};
