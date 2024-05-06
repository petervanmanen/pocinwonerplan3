import { IInburgeringstraject } from 'app/shared/model/inburgeringstraject.model';

export interface IExamen {
  id?: number;
  afgerondmetInburgeringstraject?: IInburgeringstraject | null;
}

export const defaultValue: Readonly<IExamen> = {};
