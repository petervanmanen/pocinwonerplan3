export interface IContactpersoonrol {
  id?: number;
  contactpersoonemailadres?: string | null;
  contactpersoonfunctie?: string | null;
  contactpersoonnaam?: string | null;
  contactpersoontelefoonnummer?: string | null;
}

export const defaultValue: Readonly<IContactpersoonrol> = {};
