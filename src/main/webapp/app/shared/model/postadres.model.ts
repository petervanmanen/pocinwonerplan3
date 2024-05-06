import { IWoonplaats } from 'app/shared/model/woonplaats.model';

export interface IPostadres {
  id?: number;
  postadrestype?: string | null;
  postbusofantwoordnummer?: string | null;
  postcodepostadres?: string | null;
  heeftalscorrespondentieadrespostadresinWoonplaats?: IWoonplaats | null;
}

export const defaultValue: Readonly<IPostadres> = {};
