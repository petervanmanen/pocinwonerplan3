import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { IInkomensvoorzieningsoort } from 'app/shared/model/inkomensvoorzieningsoort.model';
import { IClient } from 'app/shared/model/client.model';

export interface IInkomensvoorziening {
  id?: number;
  bedrag?: string | null;
  datumeinde?: string | null;
  datumstart?: string | null;
  datumtoekenning?: string | null;
  eenmalig?: boolean | null;
  groep?: string | null;
  emptyClientbegeleider?: IClientbegeleider | null;
  issoortvoorzieningInkomensvoorzieningsoort?: IInkomensvoorzieningsoort | null;
  voorzieningbijstandspartijClients?: IClient[] | null;
}

export const defaultValue: Readonly<IInkomensvoorziening> = {
  eenmalig: false,
};
