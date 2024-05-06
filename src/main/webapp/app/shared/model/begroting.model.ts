export interface IBegroting {
  id?: number;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IBegroting> = {};
