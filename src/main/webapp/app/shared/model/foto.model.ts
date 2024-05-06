export interface IFoto {
  id?: number;
  bestandsgrootte?: string | null;
  bestandsnaam?: string | null;
  bestandstype?: string | null;
  datumtijd?: string | null;
  locatie?: string | null;
  pixelsx?: string | null;
  pixelsy?: string | null;
}

export const defaultValue: Readonly<IFoto> = {};
