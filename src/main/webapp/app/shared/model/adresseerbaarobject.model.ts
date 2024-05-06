export interface IAdresseerbaarobject {
  id?: number;
  identificatie?: string | null;
  typeadresseerbaarobject?: string | null;
  versie?: string | null;
}

export const defaultValue: Readonly<IAdresseerbaarobject> = {};
