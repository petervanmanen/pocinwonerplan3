import { IOnderwijsloopbaan } from 'app/shared/model/onderwijsloopbaan.model';

export interface ILoopbaanstap {
  id?: number;
  klas?: string | null;
  onderwijstype?: string | null;
  schooljaar?: string | null;
  emptyOnderwijsloopbaan?: IOnderwijsloopbaan | null;
}

export const defaultValue: Readonly<ILoopbaanstap> = {};
