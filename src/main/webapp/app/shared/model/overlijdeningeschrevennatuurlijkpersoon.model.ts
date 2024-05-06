export interface IOverlijdeningeschrevennatuurlijkpersoon {
  id?: number;
  buitenlandseplaatsoverlijden?: string | null;
  buitenlandseregiooverlijden?: string | null;
  datumoverlijden?: string | null;
  gemeenteoverlijden?: string | null;
  landofgebiedoverlijden?: string | null;
  omschrijvinglocatieoverlijden?: string | null;
}

export const defaultValue: Readonly<IOverlijdeningeschrevennatuurlijkpersoon> = {};
