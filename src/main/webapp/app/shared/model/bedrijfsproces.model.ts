import dayjs from 'dayjs';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IBedrijfsproces {
  id?: number;
  afgerond?: string | null;
  datumeind?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  naam?: string | null;
  omschrijving?: string | null;
  uitgevoerdbinnenZaaks?: IZaak[];
}

export const defaultValue: Readonly<IBedrijfsproces> = {};
