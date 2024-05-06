import { IExamen } from 'app/shared/model/examen.model';

export interface IExamenonderdeel {
  id?: number;
  emptyExamen?: IExamen | null;
}

export const defaultValue: Readonly<IExamenonderdeel> = {};
