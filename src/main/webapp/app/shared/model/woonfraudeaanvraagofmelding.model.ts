export interface IWoonfraudeaanvraagofmelding {
  id?: number;
  adres?: string | null;
  categorie?: string | null;
  locatieomschrijving?: string | null;
  meldingomschrijving?: string | null;
  meldingtekst?: string | null;
}

export const defaultValue: Readonly<IWoonfraudeaanvraagofmelding> = {};
