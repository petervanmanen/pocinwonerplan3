export interface IGeluidsscherm {
  id?: number;
  aantaldeuren?: string | null;
  aantalpanelen?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IGeluidsscherm> = {};
