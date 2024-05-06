export interface IIndex {
  id?: number;
  indexnaam?: string | null;
  indexwaarde?: string | null;
}

export const defaultValue: Readonly<IIndex> = {};
