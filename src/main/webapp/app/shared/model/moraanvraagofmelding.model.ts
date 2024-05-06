export interface IMoraanvraagofmelding {
  id?: number;
  crow?: string | null;
  locatie?: string | null;
  locatieomschrijving?: string | null;
  meldingomschrijving?: string | null;
  meldingtekst?: string | null;
}

export const defaultValue: Readonly<IMoraanvraagofmelding> = {};
