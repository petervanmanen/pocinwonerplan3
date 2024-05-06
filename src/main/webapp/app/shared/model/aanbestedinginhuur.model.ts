export interface IAanbestedinginhuur {
  id?: number;
  aanvraaggesloten?: string | null;
  aanvraagnummer?: string | null;
  datumcreatie?: string | null;
  datumopeningkluis?: string | null;
  datumsluiting?: string | null;
  datumverzending?: string | null;
  fase?: string | null;
  hoogstetarief?: string | null;
  laagstetarief?: string | null;
  omschrijving?: string | null;
  perceel?: string | null;
  procedure?: string | null;
  projectnaam?: string | null;
  projectreferentie?: string | null;
  publicatie?: string | null;
  referentie?: string | null;
  status?: string | null;
  titel?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IAanbestedinginhuur> = {};
