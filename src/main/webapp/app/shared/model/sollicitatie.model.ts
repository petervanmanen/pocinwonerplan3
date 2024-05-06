import dayjs from 'dayjs';
import { IVacature } from 'app/shared/model/vacature.model';
import { ISollicitant } from 'app/shared/model/sollicitant.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface ISollicitatie {
  id?: number;
  datum?: dayjs.Dayjs | null;
  opvacatureVacature?: IVacature | null;
  solliciteertopfunctieSollicitant?: ISollicitant | null;
  solliciteertWerknemer?: IWerknemer | null;
}

export const defaultValue: Readonly<ISollicitatie> = {};
