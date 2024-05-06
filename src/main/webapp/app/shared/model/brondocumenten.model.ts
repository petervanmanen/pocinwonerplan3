export interface IBrondocumenten {
  id?: number;
  aktegemeente?: string | null;
  datumdocument?: string | null;
  documentgemeente?: string | null;
  documentidentificatie?: string | null;
  documentomschrijving?: string | null;
}

export const defaultValue: Readonly<IBrondocumenten> = {};
