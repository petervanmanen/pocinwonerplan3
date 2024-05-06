export interface IGeboorteingeschrevennatuurlijkpersoon {
  id?: number;
  buitenlandseplaatsgeboorte?: string | null;
  buitenlandseregiogeboorte?: string | null;
  datumgeboorte?: string | null;
  gemeentegeboorte?: string | null;
  landofgebiedgeboorte?: string | null;
  omschrijvinglocatiegeboorte?: string | null;
}

export const defaultValue: Readonly<IGeboorteingeschrevennatuurlijkpersoon> = {};
