import dayjs from 'dayjs';
import { IMutatie } from 'app/shared/model/mutatie.model';
import { IBatch } from 'app/shared/model/batch.model';

export interface IBatchregel {
  id?: number;
  bedrag?: number | null;
  datumbetaling?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  rekeningnaar?: string | null;
  rekeningvan?: string | null;
  leidttotMutatie?: IMutatie | null;
  heeftBatch?: IBatch | null;
}

export const defaultValue: Readonly<IBatchregel> = {};
