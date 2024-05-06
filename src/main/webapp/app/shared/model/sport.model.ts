import { ISportvereniging } from 'app/shared/model/sportvereniging.model';

export interface ISport {
  id?: number;
  naam?: string | null;
  oefentuitSportverenigings?: ISportvereniging[];
}

export const defaultValue: Readonly<ISport> = {};
