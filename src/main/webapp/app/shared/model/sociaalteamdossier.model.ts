export interface ISociaalteamdossier {
  id?: number;
  datumeinde?: string | null;
  datumstart?: string | null;
  datumvaststelling?: string | null;
  omschrijving?: string | null;
  status?: string | null;
}

export const defaultValue: Readonly<ISociaalteamdossier> = {};
