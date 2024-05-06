import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';

export interface IParticipatiedossier {
  id?: number;
  arbeidsvermogen?: string | null;
  begeleiderscode?: string | null;
  beschutwerk?: string | null;
  clientbegeleiderid?: string | null;
  datumeindebegeleiding?: string | null;
  datumstartbegeleiding?: string | null;
  indicatiedoelgroepregister?: string | null;
  emptyClientbegeleider?: IClientbegeleider;
}

export const defaultValue: Readonly<IParticipatiedossier> = {};
