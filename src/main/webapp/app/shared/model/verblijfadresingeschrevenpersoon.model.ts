export interface IVerblijfadresingeschrevenpersoon {
  id?: number;
  adresherkomst?: string | null;
  beschrijvinglocatie?: string | null;
}

export const defaultValue: Readonly<IVerblijfadresingeschrevenpersoon> = {};
