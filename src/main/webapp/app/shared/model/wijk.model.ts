import dayjs from 'dayjs';
import { IAreaal } from 'app/shared/model/areaal.model';

export interface IWijk {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: string | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  geometrie?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  status?: string | null;
  versie?: string | null;
  wijkcode?: string | null;
  wijknaam?: string | null;
  valtbinnenAreaals?: IAreaal[];
}

export const defaultValue: Readonly<IWijk> = {
  geconstateerd: false,
  inonderzoek: false,
};
