export interface ICmdbitem {
  id?: number;
  beschrijving?: string | null;
  naam?: string | null;
}

export const defaultValue: Readonly<ICmdbitem> = {};
