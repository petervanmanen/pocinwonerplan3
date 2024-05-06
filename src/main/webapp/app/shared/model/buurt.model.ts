import dayjs from 'dayjs';
import { IAreaal } from 'app/shared/model/areaal.model';

export interface IBuurt {
  id?: number;
  buurtcode?: string | null;
  buurtgeometrie?: string | null;
  buurtnaam?: string | null;
  datumbegingeldigheidbuurt?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheidbuurt?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  status?: string | null;
  versie?: string | null;
  ligtinAreaals?: IAreaal[];
}

export const defaultValue: Readonly<IBuurt> = {
  geconstateerd: false,
  inonderzoek: false,
};
