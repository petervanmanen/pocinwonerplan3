import { ITeam } from 'app/shared/model/team.model';
import { ITraject } from 'app/shared/model/traject.model';
import { IClient } from 'app/shared/model/client.model';

export interface IClientbegeleider {
  id?: number;
  begeleiderscode?: string | null;
  maaktonderdeeluitvanTeam?: ITeam | null;
  begeleidtTraject?: ITraject | null;
  ondersteuntclientClients?: IClient[] | null;
}

export const defaultValue: Readonly<IClientbegeleider> = {};
