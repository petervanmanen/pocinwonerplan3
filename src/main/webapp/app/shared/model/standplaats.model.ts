export interface IStandplaats {
  id?: number;
  adres?: string | null;
  beschrijving?: string | null;
  naaminstelling?: string | null;
}

export const defaultValue: Readonly<IStandplaats> = {};
