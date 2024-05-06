export interface IVomaanvraagofmelding {
  id?: number;
  activiteiten?: string | null;
  adres?: string | null;
  bagid?: string | null;
  dossiernummer?: string | null;
  intaketype?: string | null;
  internnummer?: string | null;
  kadastraleaanduiding?: string | null;
  kenmerk?: string | null;
  locatie?: string | null;
  locatieomschrijving?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IVomaanvraagofmelding> = {};
