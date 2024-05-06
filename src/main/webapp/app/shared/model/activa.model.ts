import { IActivasoort } from 'app/shared/model/activasoort.model';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';

export interface IActiva {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  issoortActivasoort?: IActivasoort | null;
  heeftHoofdrekenings?: IHoofdrekening[] | null;
}

export const defaultValue: Readonly<IActiva> = {};
