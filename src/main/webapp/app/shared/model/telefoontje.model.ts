import { ITelefoonstatus } from 'app/shared/model/telefoonstatus.model';
import { ITelefoononderwerp } from 'app/shared/model/telefoononderwerp.model';

export interface ITelefoontje {
  id?: number;
  afhandeltijdnagesprek?: string | null;
  deltaisdnconnectie?: string | null;
  eindtijd?: string | null;
  starttijd?: string | null;
  totaleonholdtijd?: string | null;
  totalespreektijd?: string | null;
  totalewachttijd?: string | null;
  totlatetijdsduur?: string | null;
  trackid?: string | null;
  heeftTelefoonstatus?: ITelefoonstatus | null;
  heeftTelefoononderwerp?: ITelefoononderwerp | null;
}

export const defaultValue: Readonly<ITelefoontje> = {};
