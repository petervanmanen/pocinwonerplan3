import dayjs from 'dayjs';
import { IFunctie } from 'app/shared/model/functie.model';

export interface IVacature {
  id?: number;
  datumgesloten?: dayjs.Dayjs | null;
  datumopengesteld?: string | null;
  deeltijd?: boolean | null;
  extern?: boolean | null;
  intern?: boolean | null;
  vastedienst?: boolean | null;
  vacaturebijfunctieFunctie?: IFunctie | null;
}

export const defaultValue: Readonly<IVacature> = {
  deeltijd: false,
  extern: false,
  intern: false,
  vastedienst: false,
};
