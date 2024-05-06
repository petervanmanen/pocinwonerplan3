import { IClient } from 'app/shared/model/client.model';
import { IRelatie } from 'app/shared/model/relatie.model';

export interface IHuishouden {
  id?: number;
  maaktonderdeeluitvanClients?: IClient[];
  maaktonderdeelvanRelaties?: IRelatie[] | null;
}

export const defaultValue: Readonly<IHuishouden> = {};
