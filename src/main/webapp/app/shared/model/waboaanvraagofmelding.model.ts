export interface IWaboaanvraagofmelding {
  id?: number;
  bouwkosten?: number | null;
  olonummer?: string | null;
  omschrijving?: string | null;
  projectkosten?: number | null;
  registratienummer?: string | null;
}

export const defaultValue: Readonly<IWaboaanvraagofmelding> = {};
