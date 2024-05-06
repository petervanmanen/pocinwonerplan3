import dayjs from 'dayjs';
import { IVerlofsoort } from 'app/shared/model/verlofsoort.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface IVerlof {
  id?: number;
  datumaanvraag?: dayjs.Dayjs | null;
  datumtijdeinde?: string | null;
  datumtijdstart?: string | null;
  datumtoekenning?: dayjs.Dayjs | null;
  goedgekeurd?: boolean | null;
  soortverlofVerlofsoort?: IVerlofsoort | null;
  heeftverlofWerknemer?: IWerknemer | null;
}

export const defaultValue: Readonly<IVerlof> = {
  goedgekeurd: false,
};
