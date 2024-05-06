export interface IWinkelvloeroppervlak {
  id?: number;
  aantalkassa?: string | null;
  bronwvo?: string | null;
  leegstand?: string | null;
  winkelvloeroppervlakte?: string | null;
  wvoklasse?: string | null;
}

export const defaultValue: Readonly<IWinkelvloeroppervlak> = {};
