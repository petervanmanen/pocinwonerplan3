import { IRelatiesoort } from 'app/shared/model/relatiesoort.model';
import { IHuishouden } from 'app/shared/model/huishouden.model';
import { IClient } from 'app/shared/model/client.model';

export interface IRelatie {
  id?: number;
  relatiesoort?: string | null;
  issoortRelatiesoort?: IRelatiesoort | null;
  maaktonderdeelvanHuishoudens?: IHuishouden[] | null;
  heeftrelatieClients?: IClient[] | null;
}

export const defaultValue: Readonly<IRelatie> = {};
