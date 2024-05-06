import { ISport } from 'app/shared/model/sport.model';
import { ISportlocatie } from 'app/shared/model/sportlocatie.model';

export interface ISportvereniging {
  id?: number;
  aantalnormteams?: string | null;
  adres?: string | null;
  binnensport?: boolean | null;
  buitensport?: boolean | null;
  email?: string | null;
  ledenaantal?: string | null;
  naam?: string | null;
  typesport?: string | null;
  oefentuitSports?: ISport[] | null;
  gebruiktSportlocaties?: ISportlocatie[] | null;
}

export const defaultValue: Readonly<ISportvereniging> = {
  binnensport: false,
  buitensport: false,
};
