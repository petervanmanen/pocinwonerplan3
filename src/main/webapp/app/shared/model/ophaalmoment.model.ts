import { IContainer } from 'app/shared/model/container.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IRit } from 'app/shared/model/rit.model';

export interface IOphaalmoment {
  id?: number;
  gewichtstoename?: string | null;
  tijdstip?: string | null;
  gelostContainer?: IContainer | null;
  gestoptopLocatie?: ILocatie | null;
  heeftRit?: IRit | null;
}

export const defaultValue: Readonly<IOphaalmoment> = {};
