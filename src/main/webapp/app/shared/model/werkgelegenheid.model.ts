export interface IWerkgelegenheid {
  id?: number;
  aantalfulltimemannen?: string | null;
  aantalfulltimevrouwen?: string | null;
  aantalparttimemannen?: string | null;
  aantalparttimevrouwen?: string | null;
  grootteklasse?: string | null;
}

export const defaultValue: Readonly<IWerkgelegenheid> = {};
