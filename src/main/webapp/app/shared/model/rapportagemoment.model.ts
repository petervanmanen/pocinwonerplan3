import dayjs from 'dayjs';
import { ISubsidie } from 'app/shared/model/subsidie.model';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';

export interface IRapportagemoment {
  id?: number;
  datum?: dayjs.Dayjs | null;
  naam?: string | null;
  omschrijving?: string | null;
  termijn?: string | null;
  heeftSubsidie?: ISubsidie | null;
  projectleiderRechtspersoon?: IRechtspersoon | null;
}

export const defaultValue: Readonly<IRapportagemoment> = {};
