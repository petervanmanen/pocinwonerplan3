export interface ICpvcode {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<ICpvcode> = {};
