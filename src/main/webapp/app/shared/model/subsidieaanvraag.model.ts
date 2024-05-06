import dayjs from 'dayjs';
import { ISubsidie } from 'app/shared/model/subsidie.model';
import { ISubsidiebeschikking } from 'app/shared/model/subsidiebeschikking.model';

export interface ISubsidieaanvraag {
  id?: number;
  aangevraagdbedrag?: number | null;
  datumindiening?: dayjs.Dayjs | null;
  kenmerk?: string | null;
  ontvangstbevestiging?: dayjs.Dayjs | null;
  verwachtebeschikking?: dayjs.Dayjs | null;
  betreftSubsidie?: ISubsidie;
  mondtuitSubsidiebeschikking?: ISubsidiebeschikking;
}

export const defaultValue: Readonly<ISubsidieaanvraag> = {};
