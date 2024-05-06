import { ISchool } from 'app/shared/model/school.model';
import { ISportvereniging } from 'app/shared/model/sportvereniging.model';

export interface ISportlocatie {
  id?: number;
  naam?: string | null;
  gebruiktSchools?: ISchool[] | null;
  gebruiktSportverenigings?: ISportvereniging[] | null;
}

export const defaultValue: Readonly<ISportlocatie> = {};
