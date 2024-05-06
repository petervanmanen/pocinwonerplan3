import dayjs from 'dayjs';
import { IClient } from 'app/shared/model/client.model';
import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';

export interface IBeschikking {
  id?: number;
  code?: string | null;
  commentaar?: string | null;
  datumafgifte?: dayjs.Dayjs | null;
  grondslagen?: string | null;
  wet?: string | null;
  emptyClient?: IClient | null;
  geeftafClientbegeleider?: IClientbegeleider | null;
}

export const defaultValue: Readonly<IBeschikking> = {};
