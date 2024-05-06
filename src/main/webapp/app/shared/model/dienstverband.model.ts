import dayjs from 'dayjs';
import { IFunctie } from 'app/shared/model/functie.model';
import { IUren } from 'app/shared/model/uren.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { IInzet } from 'app/shared/model/inzet.model';

export interface IDienstverband {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  periodiek?: string | null;
  salaris?: number | null;
  schaal?: string | null;
  urenperweek?: string | null;
  dienstverbandconformfunctieFunctie?: IFunctie | null;
  aantalvolgensinzetUren?: IUren | null;
  medewerkerheeftdienstverbandWerknemer?: IWerknemer;
  aantalvolgensinzetInzet?: IInzet | null;
}

export const defaultValue: Readonly<IDienstverband> = {};
