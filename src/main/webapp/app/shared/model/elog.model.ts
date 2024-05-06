export interface IElog {
  id?: number;
  korteomschrijving?: string | null;
  omschrijving?: string | null;
  tijd?: string | null;
}

export const defaultValue: Readonly<IElog> = {};
