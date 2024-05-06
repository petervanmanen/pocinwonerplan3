export interface IWoonoverlastaanvraagofmelding {
  id?: number;
  locatie?: string | null;
  locatieomschrijving?: string | null;
  meldingomschrijving?: string | null;
  meldingtekst?: string | null;
}

export const defaultValue: Readonly<IWoonoverlastaanvraagofmelding> = {};
