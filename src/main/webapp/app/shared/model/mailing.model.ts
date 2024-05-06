import dayjs from 'dayjs';
import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';

export interface IMailing {
  id?: number;
  datum?: dayjs.Dayjs | null;
  naam?: string | null;
  omschrijving?: string | null;
  versturenaanMuseumrelaties?: IMuseumrelatie[] | null;
}

export const defaultValue: Readonly<IMailing> = {};
