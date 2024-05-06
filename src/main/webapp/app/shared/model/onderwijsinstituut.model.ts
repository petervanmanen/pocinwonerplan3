import { IOpleiding } from 'app/shared/model/opleiding.model';

export interface IOnderwijsinstituut {
  id?: number;
  wordtgegevendoorOpleidings?: IOpleiding[];
}

export const defaultValue: Readonly<IOnderwijsinstituut> = {};
