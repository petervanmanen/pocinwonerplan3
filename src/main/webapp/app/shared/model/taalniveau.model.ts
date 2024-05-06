import { IClient } from 'app/shared/model/client.model';

export interface ITaalniveau {
  id?: number;
  mondeling?: string | null;
  schriftelijk?: string | null;
  heefttaalniveauClients?: IClient[];
}

export const defaultValue: Readonly<ITaalniveau> = {};
