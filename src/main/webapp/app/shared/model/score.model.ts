import { ILeefgebied } from 'app/shared/model/leefgebied.model';
import { IScoresoort } from 'app/shared/model/scoresoort.model';
import { IClient } from 'app/shared/model/client.model';

export interface IScore {
  id?: number;
  datum?: string | null;
  scorebijleeggebiedLeefgebied?: ILeefgebied | null;
  hoogtescoreScoresoort?: IScoresoort | null;
  heeftClient?: IClient | null;
}

export const defaultValue: Readonly<IScore> = {};
