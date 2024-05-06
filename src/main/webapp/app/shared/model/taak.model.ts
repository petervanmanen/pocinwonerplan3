import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { ISubsidie } from 'app/shared/model/subsidie.model';

export interface ITaak {
  id?: number;
  projectleiderRechtspersoon?: IRechtspersoon | null;
  heeftSubsidie?: ISubsidie | null;
}

export const defaultValue: Readonly<ITaak> = {};
