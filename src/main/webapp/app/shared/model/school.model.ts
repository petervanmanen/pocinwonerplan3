import { IOnderwijsloopbaan } from 'app/shared/model/onderwijsloopbaan.model';
import { IOnderwijssoort } from 'app/shared/model/onderwijssoort.model';
import { ISportlocatie } from 'app/shared/model/sportlocatie.model';

export interface ISchool {
  id?: number;
  naam?: string | null;
  kentOnderwijsloopbaans?: IOnderwijsloopbaan[];
  heeftOnderwijssoorts?: IOnderwijssoort[] | null;
  gebruiktSportlocaties?: ISportlocatie[] | null;
}

export const defaultValue: Readonly<ISchool> = {};
