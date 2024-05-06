import { IKlantbeoordeling } from 'app/shared/model/klantbeoordeling.model';

export interface IKlantbeoordelingreden {
  id?: number;
  reden?: string | null;
  heeftKlantbeoordeling?: IKlantbeoordeling | null;
}

export const defaultValue: Readonly<IKlantbeoordelingreden> = {};
