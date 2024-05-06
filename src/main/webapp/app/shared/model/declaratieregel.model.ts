import dayjs from 'dayjs';
import { IBeschikking } from 'app/shared/model/beschikking.model';
import { IClient } from 'app/shared/model/client.model';
import { IDeclaratie } from 'app/shared/model/declaratie.model';
import { IToewijzing } from 'app/shared/model/toewijzing.model';

export interface IDeclaratieregel {
  id?: number;
  bedrag?: number | null;
  code?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  isvoorBeschikking?: IBeschikking | null;
  betreftClient?: IClient | null;
  valtbinnenDeclaratie?: IDeclaratie | null;
  isopbasisvanToewijzing?: IToewijzing | null;
}

export const defaultValue: Readonly<IDeclaratieregel> = {};
