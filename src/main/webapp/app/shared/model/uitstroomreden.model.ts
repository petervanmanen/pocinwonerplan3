import { IUitstroomredensoort } from 'app/shared/model/uitstroomredensoort.model';

export interface IUitstroomreden {
  id?: number;
  datum?: string | null;
  omschrijving?: string | null;
  soortuitstroomredenUitstroomredensoort?: IUitstroomredensoort | null;
}

export const defaultValue: Readonly<IUitstroomreden> = {};
