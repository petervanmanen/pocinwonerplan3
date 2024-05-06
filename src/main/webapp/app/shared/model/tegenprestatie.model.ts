import dayjs from 'dayjs';
import { IClient } from 'app/shared/model/client.model';

export interface ITegenprestatie {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  leverttegenprestatieClient?: IClient | null;
}

export const defaultValue: Readonly<ITegenprestatie> = {};
