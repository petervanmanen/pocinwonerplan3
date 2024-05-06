import dayjs from 'dayjs';
import { ISubsidie } from 'app/shared/model/subsidie.model';

export interface ISubsidiebeschikking {
  id?: number;
  beschikkingsnummer?: string | null;
  beschiktbedrag?: number | null;
  besluit?: string | null;
  internkenmerk?: string | null;
  kenmerk?: string | null;
  ontvangen?: dayjs.Dayjs | null;
  opmerkingen?: string | null;
  betreftSubsidie?: ISubsidie | null;
}

export const defaultValue: Readonly<ISubsidiebeschikking> = {};
