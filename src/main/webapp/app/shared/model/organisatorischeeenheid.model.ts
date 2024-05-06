export interface IOrganisatorischeeenheid {
  id?: number;
  datumontstaan?: string | null;
  datumopheffing?: string | null;
  emailadres?: string | null;
  faxnummer?: string | null;
  formatie?: string | null;
  naam?: string | null;
  naamverkort?: string | null;
  omschrijving?: string | null;
  organisatieidentificatie?: string | null;
  telefoonnummer?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IOrganisatorischeeenheid> = {};
